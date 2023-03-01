package com.governance.visaagent.visaagent.port.input.rest;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisaCreateRequestResponse {
    String ticketId;
    String status;
}
