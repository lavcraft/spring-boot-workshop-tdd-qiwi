package com.governance.visaagent.visaagent.port.input.rest;

import com.governance.visaagent.visaagent.dal.VisaRequest;
import com.governance.visaagent.visaagent.service.VisaService;
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
public class CreateRequestController {
    private final VisaService visaService;

    @GetMapping("/request")
    public ResponseEntity<VisaCreateRequestResponse> createRequest(@RequestParam String userId) {
        Long id = visaService.createRequest(userId);
        return ok(
                VisaCreateRequestResponse.builder()
                                         .ticketId(id.toString())
                                         .build()
        );
    }
}
