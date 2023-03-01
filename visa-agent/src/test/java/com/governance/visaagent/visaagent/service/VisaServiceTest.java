package com.governance.visaagent.visaagent.service;

import com.governance.visaagent.visaagent.dal.VisaRequest;
import com.governance.visaagent.visaagent.dal.VisaRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisaServiceTest {
    @InjectMocks VisaService                   visaService;
    @Mock        VisaRequestRepository         visaRequestRepository;
    @Mock        KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void should_return_old_visa_request_when_user_has_one_in_processing_status() {
        //given
        when(visaRequestRepository.findAllInStatusProcessingByUserId("U-100"))
                .thenReturn(List.of(VisaRequest.builder()
                                               .id(100L)
                                               .status("processing")
                                               .userId("U-100")
                                               .build()));

        //when
        Long ticketId = visaService.createRequest("U-100");

        //then
        verify(
                visaRequestRepository,
                times(0)
        ).save(any());
        verifyNoInteractions(kafkaTemplate);
        assertThat(ticketId).isEqualTo(100L);
    }

    @Test
    void should_create_new_request_and_return_ticketId_when_no_processing_tickets_for_user() {
        //given
        when(visaRequestRepository.findAllInStatusProcessingByUserId("U-100"))
                .thenReturn(Collections.emptyList());
        when(visaRequestRepository.save(VisaRequest.builder()
                                                   .userId("U-100")
                                                   .status("processing")
                                                   .build()
        ))
                .thenReturn(
                        VisaRequest.builder().status("processing").id(100L).build()
                );

        //when
        Long ticketId = visaService.createRequest("U-100");

        //then
        verify(kafkaTemplate).send(
                "visa.requests",
                "U-100",
                "100"
        );

        assertThat(ticketId).isEqualTo(100L)
                            .describedAs("Ticket id should be 100L after save");
    }

    @Test
    void should_throw_illegal_state_exception_when_db_contain_multiple_processing_tickets_for_one_user() {
        //given
        when(visaRequestRepository.findAllInStatusProcessingByUserId("U-100"))
                .thenReturn(List.of(
                        VisaRequest.builder()
                                   .userId("U-100")
                                   .status("processing")
                                   .build(),
                        VisaRequest.builder()
                                   .userId("U-100")
                                   .status("processing")
                                   .build()
                ));

        //expect
        assertThrows(
                IllegalStateException.class,
                () -> {
                    visaService.createRequest("U-100");
                },
                "Should throw when many tickets in processing status for user U-100"
        );

    }
}
