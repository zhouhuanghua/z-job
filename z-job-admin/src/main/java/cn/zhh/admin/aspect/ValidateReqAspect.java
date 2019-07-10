package cn.zhh.admin.aspect;

import cn.zhh.admin.base.ValidateReq;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * 校验请求对象切面
 *
 * @author z_hh
 */
@Aspect
@Component
public class ValidateReqAspect {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Before("execution(* cn.zhh.admin.controller.*.*(..))")
    public void validateReq(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for(int i = 0, length = args.length; i < length; ++i) {
            Object obj = args[i];
            if (obj instanceof ValidateReq) {
                validate(obj);
            }
        }
    }

    private <T> void validate(T t) {
        // 校验对象
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, new Class[0]);
        // 存在校验错误的话，拼接所有错误信息
        if (constraintViolations.size() > 0) {
            StringBuilder validateError = new StringBuilder();

            ConstraintViolation constraintViolation;
            for(Iterator iterator = constraintViolations.iterator(); iterator.hasNext(); validateError.append(constraintViolation.getMessage()).append(";")) {

                constraintViolation = (ConstraintViolation)iterator.next();
            }
            // 抛出异常，统一异常处理器将会处理
            throw new IllegalArgumentException(validateError.toString());
        }
    }

}
