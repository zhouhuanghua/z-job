package cn.zhh.admin.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * bean工具类
 *
 * @author z_hh
 */
@Component
public class BeanUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        BeanUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return Optional.ofNullable(applicationContext).orElseGet(ContextLoader::getCurrentWebApplicationContext);
    }

    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    public static <T> List<T> listBeanBySuperclass(Class<T> superclass) {
        String[] beanNames = getApplicationContext().getBeanNamesForType(superclass);
        if (beanNames == null || beanNames.length == 0) {
            return Collections.emptyList();
        }
        return Arrays.stream(beanNames)
            .map(n -> (T)(getApplicationContext().getBean(n)))
            .collect(Collectors.toList());
    }

}
