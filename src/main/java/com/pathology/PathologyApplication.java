package com.pathology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PathologyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PathologyApplication.class, args);
		System.out.println("running the application");
	}

}
