package cn.zhh.admin.aspect;

import cn.zhh.admin.rsp.Result;
import cn.zhh.core.util.ThrowableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author z_hh
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理Throwable异常
     * @param t 异常对象
     * @return 统一Result
     */
    @ExceptionHandler(Throwable.class)
    public Result handleThrowable(Throwable t) {
        String msg = ThrowableUtils.getThrowableStackTrace(t);
        log.error("统一处理未知异常：{}", msg);
        return Result.err(msg);
    }

    /**
     * 处理非法参数异常
     * @param e 异常对象
     * @return 统一Result
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("统一处理非法参数异常：{}", ThrowableUtils.getThrowableStackTrace(e));
        return Result.err(e.getMessage());
    }
}
