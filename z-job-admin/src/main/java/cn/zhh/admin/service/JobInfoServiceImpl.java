package cn.zhh.admin.service;

import cn.zhh.admin.dao.JobInfoDao;
import cn.zhh.admin.entity.JobGroup;
import cn.zhh.admin.entity.JobInfo;
import cn.zhh.admin.enums.EnableEnum;
import cn.zhh.admin.job.JobInvoker;
import cn.zhh.admin.job.QuartzJob;
import cn.zhh.admin.rsp.Result;
import cn.zhh.admin.util.RandomGetUtils;
import cn.zhh.core.handler.JobInvokeRsp;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

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
    private JobGroupService jobGroupService;

    @Autowired
    private JobInvoker jobInvoker;

    @Override
    public Result<JobInfo> getById(Long id) {
        Optional<JobInfo> jobInfoOptional = dao.findById(id);
        if (jobInfoOptional.isPresent()) {
            return Result.ok(jobInfoOptional.get());
        }

        return Result.err("数据不存在");
    }

    @Override
    public Result<JobInfo> insert(JobInfo jobInfo) {
        if (Objects.nonNull(jobInfo.getId())) {
            jobInfo.setId(null);
        }
        JobInfo info = dao.save(jobInfo);

        // 注册
        if (Objects.equals(EnableEnum.YES.getCode(), jobInfo.getEnable())) {
            Result registerResult = register(info);
            return registerResult.isOk() ? Result.ok() : registerResult;
        }

        return Result.ok();
    }

    @Override
    public Result<JobInfo> update(JobInfo jobInfo) {
        JobInfo info = dao.save(jobInfo);

        // 修改时重新注册
        if (Objects.equals(EnableEnum.YES.getCode(), jobInfo.getEnable())) {
            Result registerResult = register(info);
            return registerResult.isOk() ? Result.ok() : registerResult;
        }

        return Result.ok();
    }

    @Override
    public Result deleteById(Long id) {
        Optional<JobInfo> jobInfoOptional = dao.findById(id);
        if (jobInfoOptional.isPresent()) {
            dao.delete(jobInfoOptional.get());
            return Result.ok();
        }
        return Result.err("数据不存在");
    }

    @Override
    public Result register(JobInfo jobInfo) {
        // 获取任务组信息
        Result<JobGroup> jobGroupResult = jobGroupService.getById(jobInfo.getJobGroup());
        if (jobGroupResult.isErr()) {
            return jobGroupResult;
        }
        JobGroup jobGroup = jobGroupResult.get();

        // 创建jobDetail实例
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)
                .withIdentity(jobInfo.getExecutorHandler(), jobGroup.getAppName())
                .withDescription(jobInfo.getJobDesc())
                .build();

        // 定义调度触发规则corn
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobInfo.getExecutorHandler(), jobGroup.getAppName())
                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                .withSchedule(CronScheduleBuilder.cronSchedule(jobInfo.getJobCron()))
                .startNow()
                .build();

        // 传递一些数据到任务里面
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("jobInfo", "jobInfo");
        jobDataMap.put("jobGroup", "jobGroup");

        // 把作业和触发器注册到任务调度中
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("注册任务异常：{}", e.getMessage(), e);
            return Result.err("注册任务异常！");
        }

        // 启动
        try {
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            log.error("启动scheduler异常：{}", e.getMessage(), e);
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
        Result<JobGroup> jobGroupResult = jobGroupService.getById(jobInfo.getJobGroup());
        if (jobGroupResult.isErr()) {
            return jobGroupResult;
        }
        JobGroup jobGroup = jobGroupResult.get();

        String addressList = jobGroup.getAddressList();
        if (!StringUtils.hasText(addressList)) {
            return Result.err("任务对应的应用没有地址信息！");
        }

        // 随机拿一个地址出来执行
        String invokeAddress = RandomGetUtils.random(Arrays.asList(jobGroup.getAddressList().split(",")));

        // 执行
        JobInvokeRsp invokeRsp = jobInvoker.invoke(invokeAddress, jobInfo.getExecutorHandler(), jobInfo.getExecutorParam());

        return invokeRsp.isOk() ? Result.ok(invokeRsp.getMsg()) : Result.err(invokeRsp.getMsg());
    }

    @Override
    public Result disable(Long id) {
        Result<JobInfo> jobInfoResult = getById(id);
        if (jobInfoResult.isErr()) {
            return jobInfoResult;
        }
        JobInfo jobInfo = jobInfoResult.get();

        // 查询对应任务组
        Result<JobGroup> jobGroupResult = jobGroupService.getById(jobInfo.getJobGroup());
        if (jobGroupResult.isErr()) {
            return jobGroupResult;
        }
        JobGroup jobGroup = jobGroupResult.get();

        JobKey jobKey = JobKey.jobKey(jobInfo.getExecutorHandler(), jobGroup.getAppName());

        try {
            boolean deleteJobResult = scheduler.deleteJob(jobKey);
            return deleteJobResult ? Result.ok("关闭定时任务成功！") : Result.err("关闭定时任务失败！");
        } catch (SchedulerException e) {
            log.error("关闭定时任务异常：{}", e.getMessage(), e);
            return Result.err("关闭定时任务异常！");
        }
    }
}
