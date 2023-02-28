package com.governance.embassy.port.input;

import com.governance.embassy.port.output.HttpClientVisaRequestResponse;
import com.governance.embassy.service.VisaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/visa")
@RequiredArgsConstructor
public class VisaRequestController {
    private final VisaService visaService;

    @GetMapping("/request")
    public ResponseEntity<VisaRequestResponse> createRequest(@RequestParam String userId) {
        HttpClientVisaRequestResponse visaRequest = visaService.createRequest(userId);

        return ok(VisaRequestResponse.builder()
                .ticketId(visaRequest.getTicketId())
                .build());
    }
}
