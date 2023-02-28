package com.governance.embassy.port.output;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class HttpClientVisaRequestResponse {
    String ticketId;
}
