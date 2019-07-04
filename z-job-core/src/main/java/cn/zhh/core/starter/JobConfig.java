package cn.zhh.core.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
public class JobConfig {

    private String adminIp;

    private Integer adminPort;

    private String appName;

    private String appDesc;

}