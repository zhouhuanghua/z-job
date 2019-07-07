package cn.zhh.core.handler;

import lombok.Data;

import java.io.Serializable;

/**
 * 任务调用请求参数
 *
 * @author zhh
 */
@Data
public class JobInvokeReq implements Serializable {
    public static final long serialVersionUID = 42L;

    private String name;

    private String params;
}
