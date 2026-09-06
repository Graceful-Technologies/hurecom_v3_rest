package com.gt.hurecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gt.hurecom")
public class HurecomApplication {

	public static void main(String[] args) {
		SpringApplication.run(HurecomApplication.class, args);
	}

}
