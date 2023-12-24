package com.locker.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"com.locker.controller", "com.locker.service", "com.locker.util", "com.locker.scheduler"})
@EntityScan("com.locker.entity")
@EnableJpaRepositories("com.locker.repository")
@EnableScheduling
public class LockerAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(LockerAppApplication.class, args);
	}
}
