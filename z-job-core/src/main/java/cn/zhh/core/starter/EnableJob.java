package cn.zhh.core.starter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({JobAutoConfiguration.class})
@interface EnableJob {
}