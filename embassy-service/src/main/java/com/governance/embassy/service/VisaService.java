package com.governance.embassy.service;

import com.governance.embassy.port.output.HttpClientVisaRequestResponse;
import com.governance.embassy.port.output.HttpClientVisaStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class VisaService {
    private final RestTemplate visaServiceRestTemplate;
    private final HttpClientProperties httpClientProperties;

    public HttpClientVisaRequestResponse createRequest(String userId) {
        ResponseEntity<HttpClientVisaRequestResponse> visaRequestResponseResponseEntity =
                visaServiceRestTemplate.postForEntity(
                httpClientProperties.getVisaAgentBaseEndpoint() + "/visa/request",
                VisaRequest.builder().userId(userId).build(),
                HttpClientVisaRequestResponse.class
        );

        HttpClientVisaRequestResponse body = visaRequestResponseResponseEntity.getBody();

        return body;
    }

    @Cacheable("visa-status")
    public HttpClientVisaStatusResponse getStatus(String ticketId) {
        ResponseEntity<HttpClientVisaStatusResponse> visaRequestResponseResponseEntity =
                visaServiceRestTemplate.getForEntity(
                httpClientProperties.getVisaAgentBaseEndpoint() + "/visa/status?ticketId=" + ticketId,
                HttpClientVisaStatusResponse.class
        );

        HttpClientVisaStatusResponse body = visaRequestResponseResponseEntity.getBody();

        return body;
    }

    @CacheEvict("visa-status")
    public void evictStatus(String ticketId) {

    }
}
