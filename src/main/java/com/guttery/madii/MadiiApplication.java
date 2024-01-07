package com.guttery.madii;

import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
public class MadiiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MadiiApplication.class, args);
	}

}
