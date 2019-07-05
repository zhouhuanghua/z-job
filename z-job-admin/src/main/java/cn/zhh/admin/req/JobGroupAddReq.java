package cn.zhh.admin.req;

import lombok.Data;

@Data
public class JobGroupAddReq {

    /**
     * 执行器AppName
     */
    private String appName;

    /**
     * 执行器名称
     */
    private String title;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 执行器地址列表，多地址逗号分隔
     */
    private String addressList;
}
