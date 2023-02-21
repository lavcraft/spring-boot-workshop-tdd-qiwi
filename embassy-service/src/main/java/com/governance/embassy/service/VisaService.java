package com.governance.embassy.service;

import com.governance.embassy.port.input.VisaStatusResponse;
import com.governance.embassy.port.output.VisaRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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

        return body;
    }

    @Cacheable("visa-status")
    public VisaStatusResponse getStatus(String ticketId) {
        ResponseEntity<VisaStatusResponse> visaRequestResponseResponseEntity = visaServiceRestTemplate.getForEntity(httpClientProperties.getVisaAgentBaseEndpoint() + "/visa/status?ticketId="+ ticketId, VisaStatusResponse.class);

        VisaStatusResponse body = visaRequestResponseResponseEntity.getBody();

        return body;
    }
}
