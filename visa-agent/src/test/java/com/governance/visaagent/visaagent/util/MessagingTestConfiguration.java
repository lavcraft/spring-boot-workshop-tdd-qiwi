package com.governance.visaagent.visaagent.util;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.Properties;

@TestConfiguration
public class MessagingTestConfiguration {
    public static KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("confluentinc" +
                                                     "/cp-kafka:7.3.1"))
                    .withReuse(true);

    static {
        Startables.deepStart(kafka).join();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            Properties              props       = new Properties();
            props.put(
                    "spring.kafka.bootstrap-servers",
                    kafka.getBootstrapServers()
            );
            environment.getPropertySources().addFirst(new PropertiesPropertySource(
                    "test-props-kafka",
                    props
            ));

        }
    }
}
