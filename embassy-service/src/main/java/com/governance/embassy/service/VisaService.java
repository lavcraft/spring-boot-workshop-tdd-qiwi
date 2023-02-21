package com.governance.embassy.service;

import com.governance.embassy.port.input.VisaStatusResponse;
import com.governance.embassy.port.output.VisaRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class VisaService {
    private final RestTemplate visaServiceRestTemplate;
    private final HttpClientProperties httpClientProperties;

    public VisaRequestResponse createRequest(String userId) {
        ResponseEntity<VisaRequestResponse> visaRequestResponseResponseEntity = visaServiceRestTemplate.postForEntity(httpClientProperties.getVisaAgentBaseEndpoint() + "/visa/request", VisaRequest.builder().userId(userId).build(), VisaRequestResponse.class);

        VisaRequestResponse body = visaRequestResponseResponseEntity.getBody();
        System.out.println("Receive ticket with id : " + body.getTicketId());

        return body;
    }

    public VisaStatusResponse getStatus(String ticketId) {
        return null;
    }
}
