package com.claim.springbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.claim")
@EntityScan("com.claim.entity")
public class SpringBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBackEndApplication.class, args);
	}

}
