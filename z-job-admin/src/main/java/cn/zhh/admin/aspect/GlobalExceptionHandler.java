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

    @ExceptionHandler(Throwable.class)
    public Result handle(Throwable t) {
        log.error("统一异常处理：{}", ThrowableUtils.getThrowableStackTrace(t));
        return Result.err(ThrowableUtils.getThrowableStackTrace(t));
    }
}
