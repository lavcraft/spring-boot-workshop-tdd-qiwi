package com.governance.visaagent.visaagent.port.input;


import com.governance.visaagent.visaagent.dal.VisaRequest;
import com.governance.visaagent.visaagent.dal.VisaRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CreateRequestControllerTest {
    @Autowired MockMvc               mockMvc;
    @MockBean  VisaRequestRepository visaRequestRepository;

    @Test
    void should_create_request_and_return_tickedId() throws Exception {
        when(visaRequestRepository.save(any()))
                .thenReturn(
                        VisaRequest.builder()
                                   .id(100L)
                                   .userId("U-100")
                                   .build()
                );

        mockMvc.perform(get("/visa/request?userId=U-100"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.ticketId").value("100"));
    }
}
