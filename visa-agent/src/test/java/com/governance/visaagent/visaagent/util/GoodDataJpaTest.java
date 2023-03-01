package com.governance.visaagent.visaagent.util;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public @interface GoodDataJpaTest {
}
