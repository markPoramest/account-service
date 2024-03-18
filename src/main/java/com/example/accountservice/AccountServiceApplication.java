package com.example.accountservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class AccountServiceApplication {

    private static final Logger logger = LogManager.getLogger(AccountServiceApplication.class);
    public static void main(String[] args) {
        logger.info("Starting Account Service Application...");
        SpringApplication.run(AccountServiceApplication.class, args);
    }

}
