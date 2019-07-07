package cn.zhh.admin.req;

import lombok.Data;

@Data
public class JobAppAddReq {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用描述
     */
    private String appDesc;

    /**
     * 应用地址列表，多个逗号分隔
     */
    private String addressList;

    /**
     * 启用状态：1-启用，0-停用
     */
    private Byte enabled;
}
