package cn.net.colin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
public class Springboot2exampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot2exampleApplication.class, args);
	}

}
