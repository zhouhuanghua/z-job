package cn.zhh.core.config;

import cn.zhh.core.executor.JobExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

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

    @Bean
    public ServletRegistrationBean jobInvokeServlet() {
        ServletRegistrationBean jobInvokeServlet = new ServletRegistrationBean(new JobInvokeServlet(), "/api/job/invoke");
        return jobInvokeServlet;
    }

    @Component
    private static class JobInvokeServlet extends HttpServlet implements ApplicationContextAware {

        private static ApplicationContext applicationContext;
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) {
            JobInvokeServlet.applicationContext = applicationContext;
        }

        private JobExecutor jobExecutor;

        public JobExecutor getJobExecutor() {
            if (Objects.nonNull(jobExecutor))
            return jobExecutor;
        }

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String name = req.getParameter("name");
            System.out.println(applicationContext);
        }
    }
}
