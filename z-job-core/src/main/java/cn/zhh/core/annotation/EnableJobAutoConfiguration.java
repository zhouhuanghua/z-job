package cn.zhh.core.annotation;

import cn.zhh.core.config.JobAutoConfigurationRegistrar;
import cn.zhh.core.config.JobConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启任务自动配置
 *
 * @author z_hh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({JobAutoConfigurationRegistrar.class, JobConfiguration.class})
public @interface EnableJobAutoConfiguration {

    String adminIp();

    int adminPort();

    String appName();

    String appDesc();
}