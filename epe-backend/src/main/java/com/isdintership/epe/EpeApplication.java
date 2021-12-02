package com.isdintership.epe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EpeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpeApplication.class, args);
	}

}
