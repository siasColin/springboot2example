package cn.net.colin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 使用注解事务时
 * @EnableTransactionManagement
 * 		springboot在TransactionAutoConfiguration已经启用，无需重复开启。
 */
@SpringBootApplication()
@MapperScan(value = "cn.net.colin.mapper.*")
public class Springboot2exampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot2exampleApplication.class, args);
	}

}
