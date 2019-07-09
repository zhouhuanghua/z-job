package cn.zhh.admin.aspect;

import cn.zhh.admin.rsp.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 对Spring的事务注解@Transactional做进一步处理，
 * 结合Service的返回值类型Result，做出是否启动事务回滚
 *
 * @author z_hh
 */
public class TransactionalAspect {

    @Around(value = "@annotation(org.springframework.transaction.annotation.Transactional)&&@annotation(transactional)")
    public Object verify(ProceedingJoinPoint pjp, Transactional transactional) throws Throwable {

        // 执行切面方法，获得返回值
        Object result = pjp.proceed();

        // 检测&强行回滚
        boolean requireRollback = requireRollback(result);
        if (requireRollback) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        // 返回切面方法运行结果
        return result;
    }

    private static boolean requireRollback(Object result) throws Exception {
        // 如果方法返回值不是Result对象，则不需要回滚
        if (!(result instanceof Result)) {
            return false;
        }

        // 如果result.isOk() == true，也不需要回滚
        Result r = (Result) result;
        if (!r.isOk()) {
            return false;
        }

        // 如果@Transactional启用了新事物(propagation = Propagation.REQUIRES_NEW)，需要回滚
        boolean isNewTransaction = TransactionAspectSupport.currentTransactionStatus().isNewTransaction();
        if (isNewTransaction) {
            return true;
        }

        // 如果方法没有被其它@Transactional注释的方法嵌套调用，说明该线程的事物已运行完毕，则需要回滚
        //  此处使用了较多的反射底层语法，强行访问Spring内部的private/protected 方法、字段，存在一定的风险
        Object currentTransactionInfo = executePrivateStaticMethod(TransactionAspectSupport.class, "currentTransactionInfo"),
                oldTransactionInfo = getPrivateFieldValue(currentTransactionInfo, "oldTransactionInfo");
        if (oldTransactionInfo == null) {
            return true;
        }

        // 其它情况，不回滚
        return false;
    }

    private static Object getPrivateFieldValue(Object target, String fieldName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    private static Object executePrivateStaticMethod(Class<?> targetClass, String methodName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = targetClass.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(null);
    }
}