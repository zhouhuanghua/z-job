package cn.zhh.core.handler;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * 任务调用返回结果
 *
 * @author z_hh
 */
@ToString
public class JobInvokeRsp implements Serializable {
    public static final long serialVersionUID = 42L;

    public static final byte SUCCESS_CODE = 1;

    public static final byte ERROR_CODE = 0;

    @Getter
    private Byte code;

    @Getter
    private String msg;

    public JobInvokeRsp() {
        this.code = SUCCESS_CODE;
    }

    public JobInvokeRsp(Byte code, String msg) {
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
