package cn.zhh.core.annotation;

import cn.zhh.core.config.EnableJobAutoConfigurationRegistrar;
import cn.zhh.core.starter.JobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({EnableJobAutoConfigurationRegistrar.class})
public @interface EnableJobAutoConfiguration {

    String adminIp();

    String adminPort();

    String appName();

    String appDesc();
}