package com.governance.embassy.service;

import com.governance.embassy.port.input.VisaStatusResponse;
import com.governance.embassy.port.output.HttpClientVisaStatusResponse;
import com.governance.embassy.service.VisaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.ConversionProcessor;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoSession;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class VisaServiceCacheableTest {
    @Autowired VisaService  visaService;
    @MockBean  RestTemplate template;

    @Test
    void should_cache_status_by_user_id() {
        when(template.getForEntity(
                "http://my-visa-agent:8888/visa/status?ticketId=T-1",
                HttpClientVisaStatusResponse.class
        ))
                .thenReturn(ResponseEntity.ok(HttpClientVisaStatusResponse.builder()
                                                                          .status("processing")
                                                                          .build()));

        when(template.getForEntity(
                "http://my-visa-agent:8888/visa/status?ticketId=T-2",
                HttpClientVisaStatusResponse.class
        ))
                .thenReturn(ResponseEntity.ok(HttpClientVisaStatusResponse.builder()
                                                                          .status("failed")
                                                                          .build()));
        when(template.getForEntity(
                "http://my-visa-agent:8888/visa/status?ticketId=T-3",
                HttpClientVisaStatusResponse.class
        ))
                .thenReturn(ResponseEntity.ok(HttpClientVisaStatusResponse.builder()
                                                                          .status("accepted")
                                                                          .build()));


        HttpClientVisaStatusResponse status1  = visaService.getStatus("T-1");
        HttpClientVisaStatusResponse status2  = visaService.getStatus("T-2");
        HttpClientVisaStatusResponse status22 = visaService.getStatus("T-2");
        HttpClientVisaStatusResponse status3  = visaService.getStatus("T-3");

        verify(
                template,
                times(3)
        ).getForEntity(
                anyString(),
                eq(HttpClientVisaStatusResponse.class)
        );
    }


    @Test
    void should_return_new_value_when_changed_avoid_cache() {
        //given
        when(template.getForEntity(
                "http://my-visa-agent:8888/visa/status?ticketId=T-1",
                HttpClientVisaStatusResponse.class
        )).thenReturn(ResponseEntity.ok(HttpClientVisaStatusResponse.builder().status("processing").build()));

        //when
        HttpClientVisaStatusResponse expectProcessing = visaService.getStatus("T-1");

        //then
        assertThat(expectProcessing.getStatus()).isEqualTo("processing");

        //and when
        when(template.getForEntity(
                "http://my-visa-agent:8888/visa/status?ticketId=T-1",
                HttpClientVisaStatusResponse.class
        )).thenReturn(ResponseEntity.ok(HttpClientVisaStatusResponse.builder().status("failed").build()));

        visaService.evictStatus("T-1");
        HttpClientVisaStatusResponse expectedFailed = visaService.getStatus("T-1");

        //then
        assertThat(expectedFailed.getStatus()).isEqualTo("failed");
    }
}