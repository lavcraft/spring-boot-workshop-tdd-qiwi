package com.governance.embassy.service;

import com.governance.embassy.port.input.VisaStatusResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class VisaServiceCacheableTest {
    @Autowired VisaService visaService;
    @MockBean RestTemplate template;

    @Test
    void should_cache_status_by_user_id() {
        when(template.getForEntity("http://my-visa-agent:8888/visa/status?ticketId=T-1", VisaStatusResponse.class))
                .thenReturn(ResponseEntity.ok(VisaStatusResponse.builder().status("processing").build()));
        when(template.getForEntity("http://my-visa-agent:8888/visa/status?ticketId=T-2", VisaStatusResponse.class))
                .thenReturn(ResponseEntity.ok(VisaStatusResponse.builder().status("failed").build()));
        when(template.getForEntity("http://my-visa-agent:8888/visa/status?ticketId=T-3", VisaStatusResponse.class))
                .thenReturn(ResponseEntity.ok(VisaStatusResponse.builder().status("accepted").build()));

        VisaStatusResponse status1 = visaService.getStatus("T-1");
        VisaStatusResponse status2 = visaService.getStatus("T-2");

        when(template.getForEntity("http://my-visa-agent:8888/visa/status?ticketId=T-1", VisaStatusResponse.class)).thenReturn(ResponseEntity.ok(VisaStatusResponse.builder().status("failed").build()));

        VisaStatusResponse status3 = visaService.getStatus("T-1");

        assertThat(status3.getStatus()).isEqualTo("processing");
        VisaStatusResponse status4 = visaService.getStatus("T-3");


        verify(template, times(3)).getForEntity(anyString(), eq(VisaStatusResponse.class));
    }
}