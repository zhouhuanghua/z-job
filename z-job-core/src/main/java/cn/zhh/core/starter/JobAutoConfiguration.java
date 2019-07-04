package cn.zhh.core.starter;

import cn.zhh.core.annotation.JobHandler;
import cn.zhh.core.handler.IJobHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 15:58
 */
@Slf4j
public class JobAutoConfiguration implements EnvironmentAware, ApplicationContextAware {
    @Autowired
    private JobConfig jobConfig;
    @Setter
    private Environment environment;
    @Setter
    private ApplicationContext applicationContext;

    @Bean(
            initMethod = "init",
            destroyMethod = "destroy"
    )
    @ConditionalOnMissingBean
    public JobExecutor jobExecutor() {
        JobExecutor jobExecutor = new JobExecutor();
        // 设置adminAddress、appName、port
        jobExecutor.setAdminAddress(Objects.requireNonNull(jobConfig.getAdminAddress(), "配置z.job.adminAddress不能为空！"));
        String appName = Optional.ofNullable(jobConfig.getAppName()).orElse(environment.getProperty("spring.application.name"));
        if (StringUtils.isEmpty(appName)) {
            throw new RuntimeException("请配置参数-》z.job.appName");
        }
        jobExecutor.setAppName(Optional.ofNullable(jobConfig.getAppName()).orElse(environment.getProperty("spring.application.name")));
        jobExecutor.setPort(Optional.ofNullable(jobConfig.getPort()).orElse(11111));
        // 加载IJobHandler
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(JobHandler.class);
        for (Object serviceBean : serviceBeanMap.values()) {
            if (serviceBean instanceof IJobHandler) {
                String name = ((JobHandler)serviceBean.getClass().getAnnotation(JobHandler.class)).value();
                IJobHandler handler = (IJobHandler)serviceBean;
                if (Objects.nonNull(jobExecutor.loadJobHandler(name))) {
                    log.error("jobhandler {} 命名冲突！", name);
                }
                jobExecutor.registJobHandler(name, handler);
            } else {
                log.warn("类{}没有实现IJobHandler接口！", serviceBean.getClass().getName());
            }
        }

        return jobExecutor;
    }

}
