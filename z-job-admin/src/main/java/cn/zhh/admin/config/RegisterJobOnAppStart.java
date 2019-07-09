package cn.zhh.admin.config;

import cn.zhh.admin.enums.EnabledEnum;
import cn.zhh.admin.enums.IsDeletedEnum;
import cn.zhh.admin.rsp.Result;
import cn.zhh.admin.service.JobInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 应用启动后注册定时任务
 *
 * @author z_hh
 */
@Component
@Slf4j
public class RegisterJobOnAppStart implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private JobInfoService jobInfoService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        jobInfoService.queryAll().stream()
            .filter(jobInfo ->
                Objects.equals(jobInfo.getIsDeleted(), IsDeletedEnum.NO.getCode())
                    && Objects.equals(jobInfo.getEnabled(), EnabledEnum.YES.getCode())
            )
            .forEach(jobInfo -> {
                Result registerResult = jobInfoService.register(jobInfo);
                if (registerResult.isErr()) {
                    log.error("任务[id={}，jobName={}]注册失败：{}", jobInfo.getId(), jobInfo.getJobName(), registerResult.getMsg());
                }
                log.info("任务[id={}，jobName={}]注册成功！", jobInfo.getId(), jobInfo.getJobName());
            });
    }
}
