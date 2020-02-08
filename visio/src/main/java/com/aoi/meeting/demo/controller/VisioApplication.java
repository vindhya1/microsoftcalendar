package com.aoi.meeting.demo.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com.aoi.meeting.demo.controller","com.aoi.meeting.demo.Entity","com.aoi.meeting.demo.repository",
		"com.aoi.meeting.demo.service","com.aoi.meeting.demo.Utility"})
 
@EnableJpaRepositories("com.aoi.meeting.demo.repository")
@EntityScan(basePackages = {"com.aoi.meeting.demo"})
public class VisioApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisioApplication.class, args);
		System.out.println("in main");
		
		
	}

}
