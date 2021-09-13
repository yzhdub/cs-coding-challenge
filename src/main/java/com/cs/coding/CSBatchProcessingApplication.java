package com.cs.coding;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CSBatchProcessingApplication {

    public static void main(String[] args) throws Exception {
        log.info("STARTING CS BATCH APPLICATION");
//        if (args.length == 0) {
//            throw new IllegalArgumentException("Please provide the location of the log file by filepath=<location>");
//        }
        System.exit(SpringApplication.exit(SpringApplication.run(CSBatchProcessingApplication.class, args)));
        log.info("APPLICATION FINISHED");
    }
}