package com.governance.embassy.port.input;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/visa")
public class VisaStatusController {
    @GetMapping("/status")
    public ResponseEntity<VisaStatusResponse> getStatus(@RequestParam String ticketId) {
        return ok(VisaStatusResponse.builder()
                .status("processing")
                .build());
    }
}
