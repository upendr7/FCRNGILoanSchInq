package com.oracle.fcr.ngi.renameIt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = "com.oracle.fcr.ngi")
public class LoanScheduleInquiryApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.oracle.fcr.ngi.renameIt.LoanScheduleInquiryApplication.class, args);
    }

}
