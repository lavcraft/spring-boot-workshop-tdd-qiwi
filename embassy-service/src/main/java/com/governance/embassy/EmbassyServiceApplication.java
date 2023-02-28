package com.governance.embassy;

import com.governance.embassy.service.HttpClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties(HttpClientProperties.class)
public class EmbassyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmbassyServiceApplication.class, args);
    }

}
