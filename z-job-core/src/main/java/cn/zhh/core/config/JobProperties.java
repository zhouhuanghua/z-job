package cn.zhh.core.config;

import lombok.Data;

/**
 * 任务配置属性类
 *
 * @author z_hh
 */
@Data
public class JobProperties {

    private String adminIp;

    private Integer adminPort;

    private String appName;

    private String appDesc;

    private String ip;

    private Integer port;
}
