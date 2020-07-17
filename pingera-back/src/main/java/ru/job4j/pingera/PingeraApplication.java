package ru.job4j.pingera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class PingeraApplication {

	public static void main(String[] args) throws InterruptedException {

		SpringApplication.run(PingeraApplication.class, args);


	}
}
