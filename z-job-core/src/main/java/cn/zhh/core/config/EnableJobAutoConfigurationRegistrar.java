package cn.zhh.core.config;

import cn.zhh.core.annotation.EnableJobAutoConfiguration;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

/**
 * TODO
 *
 * @author Zhou Huanghua
 * @date 2019/7/4 17:49
 */
public class EnableJobAutoConfigurationRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes
                .fromMap(annotationMetadata.getAnnotationAttributes(EnableJobAutoConfiguration.class.getName()));
        if (Objects.nonNull(annotationAttributes)) {
            registerJobExecutor(annotationAttributes, beanDefinitionRegistry);
        }
    }

    private void registerJobExecutor(AnnotationAttributes annotationAttributes, BeanDefinitionRegistry beanDefinitionRegistry) {
        String adminIp = annotationAttributes.getString("adminIp");
        String adminPort = annotationAttributes.getString("adminPort");
        String appName = annotationAttributes.getString("appName");
        String appDesc = annotationAttributes.getString("appDesc");
        beanDefinitionRegistry.registerBeanDefinition();
    }
}
