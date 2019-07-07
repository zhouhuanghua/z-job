package cn.zhh.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 任务配置类
 *
 * @author z_hh
 */
@Slf4j
public class JobConfiguration {
    {
        log.info("【任务调度平台】Loading JobAutoConfiguration!");
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
