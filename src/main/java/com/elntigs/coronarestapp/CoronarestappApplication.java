package com.elntigs.coronarestapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/** main class */
@SpringBootApplication
@EnableScheduling
public class CoronarestappApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronarestappApplication.class, args);
	}

}
