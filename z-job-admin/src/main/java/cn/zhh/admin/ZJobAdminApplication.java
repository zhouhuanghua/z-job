package cn.zhh.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 任务调度中心
 *
 * @author z_hh
 */
@EnableSwagger2
@SpringBootApplication
public class ZJobAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZJobAdminApplication.class, args);
	}

}
