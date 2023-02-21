package com.governance.embassy.port.output;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class VisaRequestResponse {
    String ticketId;
}
