package cn.zhh.core;

import cn.zhh.core.starter.JobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(JobAutoConfiguration.class)
public @interface EnableJobAutoConfiguration {
}