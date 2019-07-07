package cn.zhh.core.config;

import cn.zhh.core.annotation.EnableJobAutoConfiguration;
import cn.zhh.core.executor.JobExecutor;
import cn.zhh.core.util.NetUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

/**
 * ImportBeanDefinitionRegistrar
 *
 * @author z_hh
 */
@Slf4j
public class JobAutoConfigurationRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    @Setter
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes
                .fromMap(annotationMetadata.getAnnotationAttributes(EnableJobAutoConfiguration.class.getName()));
        if (Objects.isNull(annotationAttributes)) {
            log.error("【任务调度平台】EnableJobAutoConfiguration annotationAttributes is null!");
            return;
        }

        // 注册JobExecutor
        registerJobExecutor(annotationAttributes, beanDefinitionRegistry);

        // 注册Servlet
        registerJobInvokeServletRegistrationBean(beanDefinitionRegistry);

    }

    private void registerJobExecutor(AnnotationAttributes annotationAttributes, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 创建配置实例
        JobProperties jobProperties = new JobProperties();
        jobProperties.setAdminIp(annotationAttributes.getString("adminIp"));
        jobProperties.setAdminPort(annotationAttributes.getNumber("adminPort"));
        jobProperties.setAppName(annotationAttributes.getString("appName"));
        jobProperties.setAppDesc(annotationAttributes.getString("appDesc"));
        jobProperties.setIp(NetUtil.getIp());
        jobProperties.setPort(environment.getProperty("server.port", Integer.class));

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(JobExecutor.class)
                .setInitMethodName("init")
                .setDestroyMethodName("destroy")
                .addPropertyValue("jobProperties", jobProperties)
                .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
                .getBeanDefinition();

        beanDefinitionRegistry.registerBeanDefinition("jobExecutor", beanDefinition);
        log.info("【任务调度平台】JobExecutor register success!");
    }

    private void registerJobInvokeServletRegistrationBean(BeanDefinitionRegistry beanDefinitionRegistry) {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(JobInvokeServletRegistrar.class)
                .setFactoryMethod("newInstance")
                .addPropertyReference("jobExecutor", "jobExecutor")
                .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_NO)
                .getBeanDefinition();

        beanDefinitionRegistry.registerBeanDefinition("JobInvokeServlet", beanDefinition);
        log.info("【任务调度平台】JobInvokeServletRegistrar register success!");
    }

}
