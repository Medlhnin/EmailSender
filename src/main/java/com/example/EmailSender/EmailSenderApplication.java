package com.example.EmailSender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailSenderApplication {

	public static void main(String[] args) {
		System.out.println(System.getenv("SPRING_MAIL_PASSWORD"));
		System.out.println(System.getenv("SPRING_MAIL_USERNAME"));
		SpringApplication.run(EmailSenderApplication.class, args);
	}

}
