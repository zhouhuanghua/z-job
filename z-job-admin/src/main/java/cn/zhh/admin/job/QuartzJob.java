package cn.zhh.admin.job;

import cn.zhh.admin.entity.JobGroup;
import cn.zhh.admin.entity.JobInfo;
import cn.zhh.admin.entity.JobLog;
import cn.zhh.admin.service.JobLogService;
import cn.zhh.admin.util.RandomGetUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class QuartzJob implements Job {

    @Autowired
    private JobLogService jobLogService;

    @Autowired
    private JobInvoker jobInvoker;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        JobGroup jobGroup = (JobGroup) jobDataMap.get("jobGroup");
        JobInfo jobInfo = (JobInfo) jobDataMap.get("jobInfo");

        /*JobLog jobLog = new JobLog();
        jobLog.setJobGroup(jobGroup.getId());
        jobLog.setJobId(jobInfo.getId());
        jobLog.setExecutorAddress();
        jobLog.setExecutorHandler(jobInfo.getExecutorHandler());
        jobLog.setExecutorParam(jobInfo.getExecutorParam());
        jobLog.setExecutorFailRetryCount();
        jobLog.setTriggerTime(new Date());
        jobLog.setTriggerCode();
        jobLog.setHandleTime();
        jobLog.setHandleCode();
        jobLog.setAlarmStatus();*/
    }

    private void jobRun(JobGroup jobGroup, JobInfo jobInfo, JobLog jobLog) {

        // 随机获取执行器地址
        String address = RandomGetUtils.random(Arrays.asList(jobGroup.getAddressList().split(",")));
//        jobInvoker.invoke(address, )
    }
}
