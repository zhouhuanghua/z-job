package cn.zhh.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

/**
 * 异常处理工具
 *
 * @author z_hh
 */
@Slf4j
public class ThrowableUtils {

    public ThrowableUtils() {}

    public static String getThrowableStackTrace(Throwable throwable) {
        if (Objects.isNull(throwable)) {
            return null;
        }

        StringWriter stringWriter = null;
        PrintWriter printWriter = null;

        try {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);
            throwable.printStackTrace(printWriter);
            printWriter.flush();
            stringWriter.flush();
            String var3 = stringWriter.toString();
            return var3;
        } finally {
            try {
                if (Objects.nonNull(stringWriter)) {
                    stringWriter.close();
                }
                if (Objects.nonNull(printWriter)) {
                    printWriter.close();
                }
            } catch (IOException var11) {
                log.error("流对象关闭异常！", var11);
            }
        }
    }
}
