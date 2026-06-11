package com.seatbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Enable scheduling for tasks like releasing expired seat locks
public class SeatbookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeatbookingApplication.class, args);
	}

}