package com.project.ptittoanthu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EntityScan(basePackages = {"com.project.ptittoanthu"})
public class PTITEncyclopedia {

	public static void main(String[] args) {
		SpringApplication.run(PTITEncyclopedia.class, args);
	}

}
