package com.helena128.paymentmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class PaymentManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentManagerApplication.class, args);
	}

}
