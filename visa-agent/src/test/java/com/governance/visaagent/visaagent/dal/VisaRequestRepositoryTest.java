package com.governance.visaagent.visaagent.dal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VisaRequestRepositoryTest {
    @Autowired VisaRequestRepository visaRequestRepository;

    @Test
    void should_save_visa_request() {
        //when
        VisaRequest entity = visaRequestRepository.save(VisaRequest.builder()
                                                                       .userId("MY-USR-1")
                                                                       .status("processing")
                                                                       .build());

        //then
        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getUserId()).isEqualTo("MY-USR-1");
    }
}