package cn.zhh;

import cn.zhh.core.annotation.EnableJobAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJobAutoConfiguration(adminIp = "127.0.0.1",
	adminPort = 8888,
	appName = "example",
	appDesc = "示例应用")
public class ZJobExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZJobExampleApplication.class, args);
	}
}
