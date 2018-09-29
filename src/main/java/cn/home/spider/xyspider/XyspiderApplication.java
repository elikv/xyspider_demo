package cn.home.spider.xyspider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableScheduling
@MapperScan("cn.home.spider.xyspider.dao.IXyJsonDao")
public class XyspiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(XyspiderApplication.class, args);
	}
}
