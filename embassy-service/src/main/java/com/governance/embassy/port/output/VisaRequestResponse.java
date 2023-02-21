package com.governance.embassy.port.output;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisaRequestResponse {
    String ticketId;
}
