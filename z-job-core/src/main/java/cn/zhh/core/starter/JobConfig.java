package cn.zhh.core.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties( prefix = "z.job")
@Data
public class JobConfig {

    private String adminAddress;

    private String appName;

    private Integer port;
}