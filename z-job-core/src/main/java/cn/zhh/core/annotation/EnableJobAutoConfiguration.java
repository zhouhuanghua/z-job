package cn.zhh.core.annotation;

import cn.zhh.core.config.EnableJobAutoConfigurationRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @author zhh
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({EnableJobAutoConfigurationRegistrar.class})
public @interface EnableJobAutoConfiguration {

    String adminIp();

    int adminPort();

    String appName();

    String appDesc();
}