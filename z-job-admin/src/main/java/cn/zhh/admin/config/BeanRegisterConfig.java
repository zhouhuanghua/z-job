package cn.zhh.admin.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Bean注册配置类
 *
 * @author z_hh
 */
@Configuration
public class BeanRegisterConfig {

    /**
     * 注册Scheduler对象到Spring容器
     *
     * @return
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        return StdSchedulerFactory.getDefaultScheduler();
    }

    /**
     * 如果不存在RestTemplate的bean，就注册一个
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}