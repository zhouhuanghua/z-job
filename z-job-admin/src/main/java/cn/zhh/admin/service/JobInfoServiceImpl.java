package cn.zhh.admin.service;

import cn.zhh.admin.dao.JobInfoDao;
import cn.zhh.admin.entity.JobApp;
import cn.zhh.admin.entity.JobInfo;
import cn.zhh.admin.enums.EnabledEnum;
import cn.zhh.admin.enums.IsDeletedEnum;
import cn.zhh.admin.job.JobInvoker;
import cn.zhh.admin.job.QuartzJob;
import cn.zhh.admin.rsp.JobInfoPageQueryRsp;
import cn.zhh.admin.rsp.Page;
import cn.zhh.admin.rsp.Result;
import cn.zhh.admin.util.RandomGetUtils;
import cn.zhh.core.handler.JobInvokeRsp;
import cn.zhh.core.util.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 14:51
 */
@Service
@Slf4j
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoDao dao;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobAppService JobAppService;

    @Autowired
    private JobInvoker jobInvoker;

    @Override
    public Result<JobInfo> getById(Long id) {
        Optional<JobInfo> jobInfoOptional = dao.findById(id);
        if (!jobInfoOptional.isPresent()) {
            return Result.err("数据不存在！");
        }
        JobInfo jobInfo = jobInfoOptional.get();
        if (Objects.equals(jobInfo.getIsDeleted(), IsDeletedEnum.YES.getCode())) {
            return Result.err("数据不存在！");
        }
        return Result.ok(jobInfo);
    }

    @Override
    public Result<JobInfo> insert(JobInfo jobInfo) {
        if (Objects.nonNull(jobInfo.getId())) {
            jobInfo.setId(null);
        }

        // 相同应用下名称不能重复
        JobInfo jobInfoExample = new JobInfo();
        jobInfoExample.setJobAppId(jobInfo.getJobAppId());
        jobInfoExample.setJobName(jobInfo.getJobName());
        jobInfoExample.setIsDeleted(IsDeletedEnum.NO.getCode());
        boolean exists = dao.exists(Example.of(jobInfoExample));
        if (exists) {
            return Result.err("该应用已存在相同名称的任务！");
        }

        JobInfo info = save(jobInfo);

        // 注册
        if (Objects.equals(jobInfo.getEnabled(), EnabledEnum.YES.getCode())) {
            Result registerResult = register(info);
            return registerResult.isOk() ? Result.ok() : registerResult;
        }

        return Result.ok();
    }

    @Override
    public Result<JobInfo> update(JobInfo jobInfo) {
        // 启用状态不能修改
        Result<JobInfo> jobInfoResult = getById(jobInfo.getId());
        if (jobInfoResult.isErr()) {
            return jobInfoResult;
        }
        if (Objects.equals(jobInfoResult.get().getEnabled(), EnabledEnum.YES.getCode())) {
            return Result.err("任务处于启用状态，请停用后再修改！");
        }

        return Result.ok(save(jobInfo));
    }

    @Override
    public Result deleteById(Long id) {
        // 根据id查询数据
        Result<JobInfo> jobInfoResult = getById(id);
        if (jobInfoResult.isErr()) {
            return jobInfoResult;
        }
        // 修改isDeleted=1
        JobInfo jobInfo = jobInfoResult.get();
        jobInfo.setIsDeleted(IsDeletedEnum.YES.getCode());
        save(jobInfo);

        return Result.ok("删除成功！");
    }

    @Override
    public Result register(JobInfo jobInfo) {
        // 获取任务组信息
        Result<JobApp> JobAppResult = JobAppService.getById(jobInfo.getJobAppId());
        if (JobAppResult.isErr()) {
            return JobAppResult;
        }
        JobApp JobApp = JobAppResult.get();

        // 创建jobDetail实例
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                .withIdentity(jobInfo.getJobName(), JobApp.getAppName())
                .withDescription(jobInfo.getJobDesc())
                .build();

        // 定义调度触发规则corn
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobInfo.getJobName(), JobApp.getAppName())
                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(CronScheduleBuilder.cronSchedule(jobInfo.getRunCron()))
                .startNow()
                .build();

        // 传递一些数据到任务里面
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("JobApp", JobApp);
        jobDataMap.put("jobInfo", jobInfo);

        // 把作业和触发器注册到任务调度中
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("注册任务异常：{}", ThrowableUtils.getThrowableStackTrace(e));
            return Result.err("注册任务异常！");
        }

        // 启动
        try {
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            log.error("启动scheduler异常：{}", ThrowableUtils.getThrowableStackTrace(e));
            return Result.err("启动scheduler异常！");
        }

        return Result.ok();
    }

    @Override
    public Result run(Long id) {
        Result<JobInfo> jobInfoResult = getById(id);
        if (jobInfoResult.isErr()) {
            return jobInfoResult;
        }
        JobInfo jobInfo = jobInfoResult.get();

        // 查询对应任务组
        Result<JobApp> JobAppResult = JobAppService.getById(jobInfo.getJobAppId());
        if (JobAppResult.isErr()) {
            return JobAppResult;
        }
        JobApp JobApp = JobAppResult.get();

        String addressList = JobApp.getAddressList();
        if (!StringUtils.hasText(addressList)) {
            return Result.err("任务对应的应用没有地址信息！");
        }

        // 随机拿一个地址出来执行
        String invokeAddress = RandomGetUtils.random(Arrays.asList(JobApp.getAddressList().split(",")));

        // 执行
        JobInvokeRsp invokeRsp = jobInvoker.invoke(invokeAddress, jobInfo.getJobName(), jobInfo.getRunParam());

        return invokeRsp.isOk() ? Result.ok(invokeRsp.getMsg()) : Result.err(invokeRsp.getMsg());
    }

    @Override
    public Result disable(Long id) {
        // 查询和校验
        Result<JobInfo> jobInfoResult = getById(id);
        if (jobInfoResult.isErr()) {
            return jobInfoResult;
        }
        JobInfo jobInfo = jobInfoResult.get();
        if (Objects.equals(jobInfo.getEnabled(), EnabledEnum.NO.getCode())) {
            return Result.err("任务已经处于停用状态！");
        }

        // 查询对应任务组
        Result<JobApp> JobAppResult = JobAppService.getById(jobInfo.getJobAppId());
        if (JobAppResult.isErr()) {
            return JobAppResult;
        }

        // 从scheduler移除任务
        JobApp jobApp = JobAppResult.get();
        JobKey jobKey = JobKey.jobKey(jobInfo.getJobName(), jobApp.getAppName());
        try {
            boolean deleteJobResult = scheduler.deleteJob(jobKey);
            if (!deleteJobResult) {
                return Result.err("停用定时任务失败！");
            }
        } catch (SchedulerException e) {
            log.error("停用定时任务异常：{}", ThrowableUtils.getThrowableStackTrace(e));
            return Result.err("停用定时任务异常！");
        }

        // 修改数据状态
        jobInfo.setEnabled(EnabledEnum.NO.getCode());
        jobInfo.setUpdateTime(new Date());
        save(jobInfo);

        return Result.ok("停用定时任务成功！");
    }

    @Override
    public Result enable(Long id) {
        // 查询和校验
        Result<JobInfo> jobInfoResult = getById(id);
        if (jobInfoResult.isErr()) {
            return jobInfoResult;
        }
        JobInfo jobInfo = jobInfoResult.get();
        if (Objects.equals(jobInfo.getEnabled(), EnabledEnum.YES.getCode())) {
            return Result.err("任务已经处于启用状态！");
        }

        // 注册任务到scheduler
        Result registerResult = register(jobInfo);
        if (registerResult.isErr()) {
            return registerResult;
        }

        // 修改数据状态
        jobInfo.setEnabled(EnabledEnum.YES.getCode());
        jobInfo.setUpdateTime(new Date());
        save(jobInfo);

        return Result.ok("启用定时任务成功！");
    }

    @Override
    public Result<Page<JobInfoPageQueryRsp>> queryByPage(Integer pageNum, Integer pageSize) {
        // 第一页是0
        JobInfo jobInfoExample = new JobInfo();
        jobInfoExample.setIsDeleted(IsDeletedEnum.NO.getCode());
        org.springframework.data.domain.Page<JobInfo> originalPage = dao.findAll(Example.of(jobInfoExample),
                PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Order.asc("jobAppId"))));
        // 转换
        Page<JobInfoPageQueryRsp> page = Page.parse(originalPage).recoreConvert(r -> {
            JobInfoPageQueryRsp queryRsp = new JobInfoPageQueryRsp();
            BeanUtils.copyProperties(r, queryRsp, "jobAppName", "createTime", "triggerNextTime");
            // 设置任务组appName
            Result<JobApp> JobAppResult = JobAppService.getById(r.getJobAppId());
            if (JobAppResult.isOk()) {
                queryRsp.setJobAppName(JobAppResult.get().getAppName());
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 设置创建时间
            queryRsp.setCreateTime(dateFormat.format(r.getCreateTime()));
            // 设置下次调度时间
            String triggerNextTime = Objects.nonNull(r.getTriggerNextTime()) ? dateFormat.format(r.getTriggerNextTime()) : "";
            queryRsp.setTriggerNextTime(triggerNextTime);
            return queryRsp;
        });
        return Result.ok(page);
    }

    @Override
    public List<JobInfo> queryAll() {
        JobInfo jobInfoExample = new JobInfo();
        jobInfoExample.setIsDeleted(IsDeletedEnum.NO.getCode());
        List<JobInfo> jobInfoList = dao.findAll(Example.of(jobInfoExample));
        return jobInfoList;
    }

    private JobInfo save(JobInfo jobInfo) {
        return dao.save(jobInfo);
    }
}
