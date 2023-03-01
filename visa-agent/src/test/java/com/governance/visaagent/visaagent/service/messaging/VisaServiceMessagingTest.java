package com.governance.visaagent.visaagent.service.messaging;

import com.governance.visaagent.visaagent.dal.VisaRequest;
import com.governance.visaagent.visaagent.dal.VisaRequestRepository;
import com.governance.visaagent.visaagent.service.VisaService;
import com.governance.visaagent.visaagent.util.MessagingTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@MessagingTest
public class VisaServiceMessagingTest {
    @Autowired VisaService           visaService;
    @MockBean  VisaRequestRepository visaRequestRepository;
    @SpyBean   TestConfig            testConfig;

    static CountDownLatch countDownLatch = new CountDownLatch(3);

    @Test
    void should_send_notification_about_visa_request_to_queue() throws InterruptedException {
        //given
        when(visaRequestRepository.findAllInStatusProcessingByUserId("U-100"))
                .thenReturn(Collections.emptyList());
        when(visaRequestRepository.findAllInStatusProcessingByUserId("U-101"))
                .thenReturn(Collections.emptyList());
        when(visaRequestRepository.findAllInStatusProcessingByUserId("U-102"))
                .thenReturn(Collections.emptyList());

        when(visaRequestRepository.save(VisaRequest.builder()
                                                   .status("processing")
                                                   .userId("U-100")
                                                   .build()))
                .thenReturn(VisaRequest.builder().id(100L).build());
        when(visaRequestRepository.save(VisaRequest.builder()
                                                   .status("processing")
                                                   .userId("U-101")
                                                   .build()))
                .thenReturn(VisaRequest.builder().id(101L).build());
        when(visaRequestRepository.save(VisaRequest.builder()
                                                   .status("processing")
                                                   .userId("U-102")
                                                   .build()))
                .thenReturn(VisaRequest.builder().id(102L).build());


        //when
        visaService.createRequest("U-100");
        visaService.createRequest("U-101");
        visaService.createRequest("U-102");

        //then
        boolean allMessageIsReceived = countDownLatch.await(
                5,
                TimeUnit.SECONDS
        );
        assertThat(allMessageIsReceived).describedAs("All 3 messages should receive to " +
                                                     "test listened: " + countDownLatch.getCount())
                                        .isTrue();
        verify(testConfig).listen("100");
        verify(testConfig).listen("101");
        verify(testConfig).listen("102");
    }

    @TestConfiguration
    public static class TestConfig {
        @KafkaListener(topics = "visa.requests", groupId = "visa-agent")
        public void listen(String ticketId) {
            countDownLatch.countDown();
        }
    }

}
