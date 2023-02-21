package com.governance.embassy.service;

import com.governance.embassy.port.output.VisaRequestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisaServiceTest {
    @InjectMocks VisaService visaService;
    @Mock RestTemplate template;
    @Spy HttpClientProperties httpClientProperties = HttpClientProperties.builder().visaAgentBaseEndpoint("http://my-visa-agent:8081").build();

    @Test
    void should_send_visa_request_to_visa_agent_with_right_user() {
        //given
        VisaRequest request = VisaRequest.builder().userId("U-1234").build();

        when(template.postForEntity("http://my-visa-agent:8081/visa/request", request, VisaRequestResponse.class)).thenReturn(ResponseEntity.ok(VisaRequestResponse.builder().ticketId("T-1234").build()));

        //when
        VisaRequestResponse response = visaService.createRequest("U-1234");

        //then
        assertThat(response.getTicketId())
                .isEqualTo("T-1234")
                .describedAs("ticket id should be T-1234 b ecause http client return it for user U-1234");
    }
}
