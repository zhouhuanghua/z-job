package cn.zhh.core.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * 任务调用返回结果
 *
 * @author zhh
 */
@ToString
public class JobInvokeRsp implements Serializable {
    public static final long serialVersionUID = 42L;

    private static final int SUCCESS_CODE = 200;

    private static final int ERROR_CODE = 500;

    private int code;

    @Getter
    private String msg;

    private JobInvokeRsp(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static JobInvokeRsp success(String msg) {
        return new JobInvokeRsp(SUCCESS_CODE, msg);
    }

    public static JobInvokeRsp error(String msg) {
        return new JobInvokeRsp(ERROR_CODE, msg);
    }

    public boolean isOk() {
        return Objects.equals(this.code, SUCCESS_CODE);
    }

}
