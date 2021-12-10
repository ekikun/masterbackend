package com.eki.backend;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.eki.masterbackend.mapper")
public class MasterbackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(MasterbackendApplication.class, args);
	}
}
