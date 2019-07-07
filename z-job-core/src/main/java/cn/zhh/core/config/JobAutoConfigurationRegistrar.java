package cn.zhh.core.config;

import cn.zhh.core.annotation.EnableJobAutoConfiguration;
import cn.zhh.core.starter.JobExecutor;
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
 * TODO
 *
 * @author zhh
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
            log.warn("EnableJobAutoConfiguration annotationAttributes is null！");
            return;
        }

        // 注册JobExecutor
        registerJobExecutor(annotationAttributes, beanDefinitionRegistry);
    }

    private void registerJobExecutor(AnnotationAttributes annotationAttributes, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 创建配置实例
        JobConfig jobConfig = new JobConfig();
        jobConfig.setAdminIp(annotationAttributes.getString("adminIp"));
        jobConfig.setAdminPort(annotationAttributes.getNumber("adminPort"));
        jobConfig.setAppName(annotationAttributes.getString("appName"));
        jobConfig.setAppDesc(annotationAttributes.getString("appDesc"));
        jobConfig.setIp(NetUtil.getIp());
        jobConfig.setPort(environment.getProperty("server.port", Integer.class));

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(JobExecutor.class)
                .setInitMethodName("init")
                .setDestroyMethodName("destroy")
                .addPropertyValue("jobConfig", jobConfig)
                .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE)
                .getBeanDefinition();

        beanDefinitionRegistry.registerBeanDefinition("jobExecutor", beanDefinition);
    }
}