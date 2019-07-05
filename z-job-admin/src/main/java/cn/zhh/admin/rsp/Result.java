package cn.zhh.admin.rsp;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

@ToString
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 6547662806723050209L;
    private static final int SUCCESS = 200;
    private static final int ERROR = 500;

    @Getter
    private Integer code;
    @Getter
    private String msg;
    @Getter
    private T content;

    private Result(Integer code, String msg, T content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public static <T> Result<T> ok() {
        return new Result<>(SUCCESS, null, (T)null);
    }

    public static <T> Result<T> ok(String msg) {
        return new Result<>(SUCCESS, msg, (T)null);
    }

    public static <T> Result<T> ok(T content) {
        return new Result<>(SUCCESS, null, content);
    }

    public static <T> Result<T> ok(String msg, T content) {
        return new Result<>(SUCCESS, msg, content);
    }

    public static <T> Result<T> err() {
        return new Result<>(ERROR, null, (T)null);
    }

    public static <T> Result<T> err(String msg) {
        return new Result<>(ERROR, msg, (T)null);
    }

    public boolean isOk() {
        return Objects.equals(this.code, SUCCESS);
    }

    public boolean isErr() {
        return Objects.equals(this.code, ERROR);
    }

    public T get() {
        if (isErr()) {
            throw new UnsupportedOperationException("result is error!");
        }
        return this.content;
    }
}
