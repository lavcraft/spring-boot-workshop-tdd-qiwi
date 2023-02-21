package com.governance.embassy;

import com.governance.embassy.port.input.VisaStatusResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VisaStatusControllerTest {
    @Autowired TestRestTemplate template;

    @Test
    void should_return_status_by_ticketId() {
        //when
        ResponseEntity<VisaStatusResponse> response = template.getForEntity("/visa/status?ticketId=T-122", VisaStatusResponse.class);

        //then
        assertTrue(response.getStatusCode().is2xxSuccessful(), "should return 200 -> actual:" + response.getStatusCode());
        assertEquals("processing", response.getBody().getStatus());
    }
}
