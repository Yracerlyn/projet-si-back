package com.projetsiback.projetsiback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class ProjetSiBackApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProjetSiBackApplication.class, args);
	}

}
