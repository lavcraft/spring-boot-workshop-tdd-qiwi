package com.governance.visaagent.visaagent.dal;

import com.governance.visaagent.visaagent.util.GoodDataJpaTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@GoodDataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VisaRequestRepositoryTest {
    @Autowired VisaRequestRepository visaRequestRepository;

    @Test
    void should_populate_db_via_default_visa_request() {
        Optional<VisaRequest> entity = visaRequestRepository.findById(10L);
        assertThat(entity).isPresent();
    }

    @Test
    void should_save_visa_request() {
        //when
        VisaRequest entity = visaRequestRepository.save(
                VisaRequest.builder()
                           .userId("MY-USR-1")
                           .status("processing")
                           .build()
        );

        //then
        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getUserId()).isEqualTo("MY-USR-1");
    }

    @Test
    void should_find_all_tickets_for_user() {
        //given
        visaRequestRepository.save(
                VisaRequest.builder()
                           .userId("U-1")
                           .status("processing")
                           .build()
        );
        visaRequestRepository.save(
                VisaRequest.builder()
                           .userId("U-1")
                           .status("processing")
                           .build()
        );

        //when
        List<VisaRequest> requests =
                visaRequestRepository.findAllInStatusProcessingByUserId("U-1");

        assertThat(requests).hasSize(2);
        assertThat(requests.get(0).getStatus()).isEqualTo("processing");
        assertThat(requests.get(1).getStatus()).isEqualTo("processing");
    }

    @Test
    void should_update_status_field() {
        //given
        VisaRequest entity = visaRequestRepository.save(
                VisaRequest.builder()
                           .userId("U-1")
                           .status("processing")
                           .build()
        );

        //when
        visaRequestRepository.updateStatusById("failed", entity.getId());

        //then
        Optional<VisaRequest> newEntity = visaRequestRepository.findById(entity.getId());

        assertThat(newEntity).isPresent();
        assertThat(newEntity.get().getStatus()).isEqualTo("failed");
    }
}