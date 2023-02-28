package com.governance.embassy.service;

import com.governance.embassy.port.output.HttpClientVisaStatusResponse;
import com.governance.embassy.port.output.HttpClientVisaRequestResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VisaServiceTest {
    @InjectMocks VisaService          visaService;
    @Mock()        RestTemplate         template;
    @Spy         HttpClientProperties httpClientProperties = HttpClientProperties.builder()
                                                                                 .visaAgentBaseEndpoint("http://my-visa-agent:8081")
                                                                                 .build();
    @Test
    void should_send_visa_request_to_visa_agent_with_right_user() {
        //given
        VisaRequest request = VisaRequest.builder().userId("U-1234").build();

        when(template.postForEntity(
                "http://my-visa-agent:8081/visa/request",
                request,
                HttpClientVisaRequestResponse.class
        )).thenReturn(ResponseEntity.ok(HttpClientVisaRequestResponse.builder().ticketId("T-1234").build()));

        //when
        HttpClientVisaRequestResponse response = visaService.createRequest("U-1234");

        //then
        assertThat(response.getTicketId())
                .isEqualTo("T-1234")
                .describedAs("ticket id should be T-1234 b ecause http client return it for user " +
                             "U-1234");
    }

    @Test
    void should_send_http_request_for_fetch_status_from_visa_agent() {
        //given
        when(template.getForEntity(
                "http://my-visa-agent:8081/visa/status?ticketId=T-1234",
                HttpClientVisaStatusResponse.class
        )).thenReturn(ResponseEntity.ok(
                HttpClientVisaStatusResponse.builder()
                                            .status("processing")
                                            .build()
        ));

        //when
        HttpClientVisaStatusResponse status = visaService.getStatus("T-1234");


        //then
        assertThat(status.getStatus())
                .isEqualTo("processing")
                .describedAs("service should return processing status from external visa-agent");
    }
}
