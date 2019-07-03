package cn.zhh;

import cn.zhh.core.EnableJobAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJobAutoConfiguration
public class ZJobExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZJobExampleApplication.class, args);
	}

}
