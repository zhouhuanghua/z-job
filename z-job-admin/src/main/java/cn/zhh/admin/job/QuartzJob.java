package cn.zhh.admin.job;

import cn.zhh.admin.entity.JobApp;
import cn.zhh.admin.entity.JobInfo;
import cn.zhh.admin.entity.JobLog;
import cn.zhh.admin.util.BeanUtils;
import cn.zhh.admin.util.MailSendService;
import cn.zhh.core.handler.JobInvokeRsp;
import cn.zhh.core.util.ThrowableUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Quartz任务
 *
 * @author z_hh
 */
@Slf4j
public class QuartzJob implements Job {

    private static final byte SUCCESS = 1;
    private static final byte ERROR = 0;

    private JobInvoker jobInvoker;

    private MailSendService mailSendService;

    public QuartzJob() {
        this.jobInvoker = BeanUtils.getBean(JobInvoker.class);
        this.mailSendService = BeanUtils.getBean(MailSendService.class);
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
            String msg = ThrowableUtils.getThrowableStackTrace(t);
            log.warn("任务{}调度出现异常：{}", jobInfo.getJobName(), msg);
            jobLog.setTriggerResult((byte)0);
            jobLog.setTriggerMsg("调度异常：" + msg);
        }
        jobLog.setTriggerEndTime(new Date());
        // 记录任务的本次和下次调用时间
        jobInfo.setTriggerLastTime(jobExecutionContext.getFireTime());
        jobInfo.setTriggerNextTime(jobExecutionContext.getNextFireTime());

        // 插入日志
        jobLog.save();

        // 更新任务
        jobInfo.save();

        // 发送邮件
        sendMail(jobApp, jobInfo, jobLog);

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
                String msg = ThrowableUtils.getThrowableStackTrace(t);
                log.warn("调用{}的{}任务时出现异常：{}", address, jobInfo.getJobName(), msg);
                jobInvokeRsp = JobInvokeRsp.error("任务调用异常：" + msg);
            }
            iterator.remove();
        }
        if (Objects.isNull(jobInvokeRsp)) {
            jobInvokeRsp = JobInvokeRsp.error("没有进行任务调用！");
        }
        jobLog.setJobRunResult(jobInvokeRsp.getCode());
        jobLog.setJobRunMsg(sub3000String(jobInvokeRsp.getMsg()));

        jobLog.setRunFailRetryCount(Objects.equals(readyRetryCount, -1) ? 0 : readyRetryCount);
        jobLog.setRunAddressList(hasInvokeAddress.stream().reduce((s1, s2) -> s1 + "," + s2).orElse(""));
    }

    private void sendMail(JobApp jobApp, JobInfo jobInfo, JobLog jobLog) {
        // 调度失败并且邮件不为空
        if ((Objects.equals(jobLog.getTriggerResult(), (byte)0)
                || Objects.equals(jobLog.getJobRunResult(), (byte)0))
                && StringUtils.hasText(jobInfo.getAlarmEmail())) {
            String alarmEmailStr = jobInfo.getAlarmEmail();
            List<String> mailList = null;
            if (alarmEmailStr.contains(",")) {
                mailList = Arrays.asList(alarmEmailStr.split(","));
            } else {
                mailList = Collections.singletonList(alarmEmailStr);
            }
            String subject = String.format("应用%s的%s任务调度失败！", jobApp.getAppName(), jobInfo.getJobName());
            try {
                String content = new ObjectMapper().writeValueAsString(jobLog);
                mailList.forEach(m -> {
                    mailSendService.sendSimpleMail(m, subject, content);
                });
            } catch (JsonProcessingException e) {
                log.error("任务调度失败告警邮件发送失败：{}", ThrowableUtils.getThrowableStackTrace(e));
            }
        }
    }

    private String sub3000String(String str) {
        if (StringUtils.hasText(str) && str.length() > 3000) {
            return str.substring(0, 2888) + "...更多请查看日志记录！";
        }
        return str;
    }
}
