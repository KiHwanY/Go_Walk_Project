package com.cos.gowalk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cos.gowalk.repository")
public class GowalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(GowalkApplication.class, args);
	}

}
