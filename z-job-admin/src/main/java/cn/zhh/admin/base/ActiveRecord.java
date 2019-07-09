package cn.zhh.admin.base;

import cn.zhh.admin.util.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import javax.persistence.Id;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * AR模式的实体基类
 *
 * @author z_hh
 */
public class ActiveRecord<T extends ActiveRecord, ID, DAO> {

    private JpaRepository<T, ID> jpaRepository;

    /**
     * 达到延迟加载的效果
     *
     * @return dao对象
     */
    private JpaRepository<T, ID> dao() {
        return Optional.ofNullable(jpaRepository).orElseGet(() -> {
            Type type = this.getClass().getGenericSuperclass();
            Type[] parameter = ((ParameterizedType) type).getActualTypeArguments();
            Class<DAO> daoClazz = (Class<DAO>)parameter[2];
            if (daoClazz.isAnnotationPresent(Repository.class)) {
                Repository annotation = daoClazz.getAnnotation(Repository.class);
                return jpaRepository = (JpaRepository<T, ID>) BeanUtils.getBean(annotation.value());
            }
            String clazzName = daoClazz.getSimpleName();
            String beanName = clazzName.substring(0, 1).toLowerCase() + clazzName.substring(1);
            return jpaRepository = (JpaRepository<T, ID>) BeanUtils.getBean(beanName);
        });
    }

    public T save() {
        return dao().save((T)this);
    }

    public void deleteById() {
        dao().deleteById(pkVal());
    }

    public List<T> findAllByExample() {
        return dao().findAll(Example.of((T)this));
    }

    public Optional<T> findById() {
        return dao().findById(pkVal());
    }

    /**
     * 推荐子类重写该方法，返回主键的值
     * @return
     */
    protected ID pkVal() {
        return Arrays.stream(this.getClass().getDeclaredFields())
            .filter(f -> f.isAnnotationPresent(Id.class))
            .map(f -> {
                f.setAccessible(true);
                return (ID) ReflectionUtils.getField(f, this);
            })
            .findAny()
            .orElse((ID)null);

    }
}
