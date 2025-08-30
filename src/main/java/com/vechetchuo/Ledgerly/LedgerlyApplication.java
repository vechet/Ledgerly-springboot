package com.vechetchuo.Ledgerly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.vechetchuo.Ledgerly.repositories")
public class LedgerlyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LedgerlyApplication.class, args);
	}

}
