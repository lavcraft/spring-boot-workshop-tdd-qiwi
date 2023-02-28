package com.governance.embassy;

import com.governance.embassy.port.input.VisaStatusResponse;
import com.governance.embassy.port.output.HttpClientVisaStatusResponse;
import com.governance.embassy.service.VisaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VisaStatusControllerTest {
    @Autowired TestRestTemplate template;
    @MockBean  VisaService      visaService;

    @Test
    void should_return_status_by_ticketId() {
        //given
        when(visaService.getStatus("T-122")).thenReturn(HttpClientVisaStatusResponse.builder()
                                                                                    .status("processing")
                                                                                    .build());

        //when
        ResponseEntity<VisaStatusResponse> response = template.getForEntity(
                "/visa/status?ticketId=T-122",
                VisaStatusResponse.class
        );

        //then
        assertTrue(
                response.getStatusCode().is2xxSuccessful(),
                "should return 200 -> actual:" + response.getStatusCode()
        );
        assertEquals(
                "processing",
                response.getBody().getStatus()
        );
    }

    @Test
    void should_return_failed_status_by_ticketId() {
        //given
        when(visaService.getStatus("T-122")).thenReturn(HttpClientVisaStatusResponse.builder()
                                                                          .status("failed")
                                                                          .build());

        //when
        ResponseEntity<VisaStatusResponse> response = template.getForEntity(
                "/visa/status?ticketId=T-122",
                VisaStatusResponse.class
        );

        //then
        assertTrue(
                response.getStatusCode().is2xxSuccessful(),
                "should return 200 -> actual:" + response.getStatusCode()
        );
        assertEquals(
                "failed",
                response.getBody().getStatus()
        );
    }

    @Test
    void should_return_unknown_status_when_visa_service_return_null() {
        //given
        when(visaService.getStatus("T-122")).thenReturn(null);

        //when
        ResponseEntity<VisaStatusResponse> response = template.getForEntity(
                "/visa/status?ticketId=T-122",
                VisaStatusResponse.class
        );

        //then
        assertTrue(
                response.getStatusCode().is4xxClientError(),
                "should return 404 -> actual:" + response.getStatusCode()
        );
        assertNotNull(
                response.getBody(),
                "expect response with unknown status, but found null body"
        );
        assertEquals(
                "unknown",
                response.getBody().getStatus()
        );
    }

    @Test
    void should_return_unknown_status_when_visa_service_throw() {
        //given
        when(visaService.getStatus("T-122")).thenThrow(new RuntimeException("sth went wrong"));

        //when
        ResponseEntity<VisaStatusResponse> response = template.getForEntity(
                "/visa/status?ticketId=T-122",
                VisaStatusResponse.class
        );

        //then
        assertTrue(
                response.getStatusCode().is5xxServerError(),
                "should return 500 -> actual:" + response.getStatusCode()
        );
        assertNotNull(
                response.getBody(),
                "expect response with unknown status, but found null body"
        );
        assertEquals(
                "unknown",
                response.getBody().getStatus()
        );
    }
}
