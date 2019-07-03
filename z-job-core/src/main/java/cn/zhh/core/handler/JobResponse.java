package cn.zhh.core.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 任务执行返回值
 *
 * @author Zhou Huanghua
 * @date 2019/7/3 15:39
 */
@ToString
public class JobResponse<T> implements Serializable {
    public static final long serialVersionUID = 42L;

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;

    public static final JobResponse<String> SUCCESS = new JobResponse<String>(null);
    public static final JobResponse<String> FAIL = new JobResponse<String>(FAIL_CODE, null);

    @Getter @Setter
    private int code;
    @Getter @Setter
    private String msg;
    @Getter @Setter
    private T content;

    public JobResponse(){}

    public JobResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JobResponse(T content) {
        this.code = SUCCESS_CODE;
        this.content = content;
    }
}
