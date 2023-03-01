package com.governance.visaagent.visaagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Clock;
import java.time.ZonedDateTime;

@SpringBootApplication
public class VisaAgentApplication {

    public static void main(String[] args) {
		SpringApplication.run(VisaAgentApplication.class, args);
	}

}
