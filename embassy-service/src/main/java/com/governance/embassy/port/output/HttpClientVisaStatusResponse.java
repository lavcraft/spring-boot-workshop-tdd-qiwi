package com.governance.embassy.port.output;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpClientVisaStatusResponse {
    String status;
}
