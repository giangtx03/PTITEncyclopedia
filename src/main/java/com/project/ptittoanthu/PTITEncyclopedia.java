package com.project.ptittoanthu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PTITEncyclopedia {

	public static void main(String[] args) {
		SpringApplication.run(PTITEncyclopedia.class, args);
	}

}
