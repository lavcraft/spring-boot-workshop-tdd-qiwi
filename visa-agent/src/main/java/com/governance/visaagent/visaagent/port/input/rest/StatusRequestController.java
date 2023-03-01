package com.governance.visaagent.visaagent.port.input.rest;

import com.governance.visaagent.visaagent.dal.VisaRequest;
import com.governance.visaagent.visaagent.service.VisaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/visa")
@RequiredArgsConstructor
public class StatusRequestController {
    private final VisaService visaService;

    @GetMapping("/status")
    public ResponseEntity<VisaCreateRequestResponse> createRequest(@RequestParam Long ticketId) {
        Optional<VisaRequest> entityOptional = visaService.findById(ticketId);
        if (entityOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        VisaRequest entity = entityOptional.get();
        return ok(
                VisaCreateRequestResponse.builder()
                                         .ticketId(entity.getId().toString())
                                         .status(entity.getStatus())
                                         .build()
        );
    }
}
