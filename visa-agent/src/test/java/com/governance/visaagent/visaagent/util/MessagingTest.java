package com.governance.visaagent.visaagent.util;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SpringBootTest(
        properties = {
                "spring.autoconfigure.exclude[0]=org.springframework.boot.autoconfigure.flyway" +
                ".FlywayAutoConfiguration",
                "spring.autoconfigure.exclude[1]=org.springframework.boot.autoconfigure.orm.jpa" +
                ".HibernateJpaAutoConfiguration",
                "spring.autoconfigure.exclude[2]=org.springframework.boot.autoconfigure.data.jpa" +
                ".JpaRepositoriesAutoConfiguration",
        }
)
@ContextConfiguration(
        classes = MessagingTestConfiguration.class,
        initializers = MessagingTestConfiguration.Initializer.class
)
public @interface MessagingTest {
}
