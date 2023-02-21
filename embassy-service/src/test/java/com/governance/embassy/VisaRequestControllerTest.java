package com.governance.embassy;

import com.governance.embassy.port.input.VisaRequestResponse;
import com.governance.embassy.service.VisaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VisaRequestControllerTest {
    @Autowired TestRestTemplate template;
    @MockBean VisaService visaService;

    @Test
    void should_accept_request_and_return_ok() {
        //given
        when(visaService.createRequest(anyString())).thenReturn(com.governance.embassy.port.output.VisaRequestResponse.builder().ticketId("T-112").build());

        //when
        ResponseEntity<String> response = template.getForEntity("/visa/request?userId=U-111", String.class);

        //given
        assertTrue(response.getStatusCode().is2xxSuccessful(), "response should be 2xx but not => " + response.getStatusCode());
    }

    @Test
    void should_return_ticketId_when_visa_request_created() {
        //given
        when(visaService.createRequest("U-111")).thenReturn(com.governance.embassy.port.output.VisaRequestResponse.builder().ticketId("T-112").build());

        //when
        ResponseEntity<VisaRequestResponse> response = template.getForEntity("/visa/request?userId=U-111", VisaRequestResponse.class);

        //given
        VisaRequestResponse body = response.getBody();

        verify(visaService).createRequest("U-111");
        assertNotNull(body, "body should not be null");
        assertEquals("T-112", body.getTicketId(), "ticket id should be T-111");
    }
}
