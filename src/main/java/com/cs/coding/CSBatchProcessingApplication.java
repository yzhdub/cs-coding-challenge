package com.cs.coding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CSBatchProcessingApplication {

    private static final Logger log = LoggerFactory.getLogger(CSBatchProcessingApplication.class);

    public static void main(String[] args) throws Exception {
        log.info("STARTING CS BATCH APPLICATION");
        System.exit(SpringApplication.exit(SpringApplication.run(CSBatchProcessingApplication.class, args)));
        log.info("APPLICATION FINISHED");
    }
}