package com.bit.studypage;
import com.bit.studypage.controller.ApiController;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiniTestApplication {
	public static void main(String[] args) {
		SpringApplication.run(MiniTestApplication.class, args);
	}

}
