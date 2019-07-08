package cn.zhh.admin.job;

import cn.zhh.admin.entity.JobApp;
import cn.zhh.admin.entity.JobInfo;
import cn.zhh.admin.entity.JobLog;
import cn.zhh.admin.util.BeanUtils;
import cn.zhh.core.handler.JobInvokeRsp;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class QuartzJob implements Job {

    private static final byte SUCCESS = 1;
    private static final byte ERROR = 0;

    private JobInvoker jobInvoker;

    public QuartzJob() {
        this.jobInvoker = BeanUtils.getBean(JobInvoker.class);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        JobApp jobApp = (JobApp) jobDataMap.get("JobApp");
        JobInfo jobInfo = (JobInfo) jobDataMap.get("jobInfo");
        log.info("任务{}开始调度...", jobInfo.getJobName());

        JobLog jobLog = new JobLog();
        jobLog.setJobId(jobInfo.getId());
        jobLog.setTriggerStartTime(new Date());
        try {
            jobRun(jobApp, jobInfo, jobLog);
            jobLog.setTriggerResult((byte)1);
            jobLog.setTriggerMsg("调度成功！");
        } catch (Throwable t) {
            log.warn("任务{}调度出现异常：{}", jobInfo.getJobName(), t.getMessage(), t);
            jobLog.setTriggerResult((byte)0);
            jobLog.setTriggerMsg("调度失败：" + t.getMessage());
        }
        jobLog.setTriggerEndTime(new Date());
        // 记录任务的本次和下次调用时间
        jobInfo.setTriggerLastTime(jobExecutionContext.getFireTime());
        jobInfo.setTriggerNextTime(jobExecutionContext.getNextFireTime());

        // 插入日志
        jobLog.save();

        // 更新任务
        jobInfo.save();

        log.info("任务{}调度结束！", jobInfo.getJobName());
    }

    private void jobRun(JobApp jobApp, JobInfo jobInfo, JobLog jobLog) {

        List<String> addressList = Arrays.stream(jobApp.getAddressList().split(","))
                .filter(StringUtils::hasText)
                .collect(Collectors.toList());
        Iterator<String> iterator = addressList.iterator();
        JobInvokeRsp jobInvokeRsp = null;
        List<String> hasInvokeAddress = new ArrayList<>();
        int failRetryCount = jobInfo.getRunFailRetryCount(),
                readyRetryCount = -1;
        while (iterator.hasNext() && ++readyRetryCount <= failRetryCount) {
            String address = iterator.next();
            hasInvokeAddress.add(address);
            try {
                jobInvokeRsp = jobInvoker.invoke(address, jobInfo.getJobName(), jobInfo.getRunParam());
                if (jobInvokeRsp.isOk()) {
                    break;
                }
                log.warn("调用{}的{}任务失败：{}", address, jobInfo.getJobName(), jobInvokeRsp.getMsg());
            } catch (Throwable t) {
                log.warn("调用{}的{}任务时出现异常：{}", address, jobInfo.getJobName(), t.getMessage(), t);
                jobInvokeRsp = JobInvokeRsp.error("任务调用异常！");
            }
            iterator.remove();
        }
        if (Objects.isNull(jobInvokeRsp)) {
            jobInvokeRsp = JobInvokeRsp.error("没有进行任务调用！");
        }
        jobLog.setJobRunResult(jobInvokeRsp.getCode());
        jobLog.setJobRunMsg(jobInvokeRsp.getMsg());

        jobLog.setRunFailRetryCount(Objects.equals(readyRetryCount, -1) ? 0 : readyRetryCount);
        jobLog.setRunAddressList(hasInvokeAddress.stream().reduce((s1, s2) -> s1 + "," + s2).orElse(""));
    }
}
