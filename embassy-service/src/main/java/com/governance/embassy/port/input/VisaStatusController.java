package com.governance.embassy.port.input;

import com.governance.embassy.port.output.HttpClientVisaStatusResponse;
import com.governance.embassy.service.VisaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/visa")
@RequiredArgsConstructor
public class VisaStatusController {
    private final VisaService visaService;

    @GetMapping("/status")
    public ResponseEntity<VisaStatusResponse> getStatus(@RequestParam String ticketId) {
        HttpClientVisaStatusResponse status;
        status = visaService.getStatus(ticketId);

        if (status == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(VisaStatusResponse.builder().status("unknown").build());
        }

        return ok(
                VisaStatusResponse.builder()
                                  .status(status.getStatus())
                                  .build()
        );
    }

//    @ControllerAdvice
//    public static class HandleErrorAdvice {
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<VisaStatusResponse> visaStatusResponseResponseEntity() {
            return ResponseEntity.internalServerError()
                                 .body(VisaStatusResponse.builder().status("unknown").build());
        }
//    }
}
