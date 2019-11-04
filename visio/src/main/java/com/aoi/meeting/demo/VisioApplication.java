package com.aoi.meeting.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ="com.aoi.meeting.demo")
public class VisioApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisioApplication.class, args);
		System.out.println("in main");
		
		
	}

}
