package com.ingescompany.doofplus.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class IngesCompanyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngesCompanyBackendApplication.class, args);
	}

}
