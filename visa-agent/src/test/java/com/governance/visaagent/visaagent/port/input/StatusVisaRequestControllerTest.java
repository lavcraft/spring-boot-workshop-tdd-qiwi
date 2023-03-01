package com.governance.visaagent.visaagent.port.input;

import com.governance.visaagent.visaagent.dal.VisaRequest;
import com.governance.visaagent.visaagent.dal.VisaRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StatusVisaRequestControllerTest {
    @Autowired MockMvc               mockMvc;
    @MockBean  VisaRequestRepository visaRequestRepository;

    @Test
    void should_return_status_b_ticketId() throws Exception {
        when(visaRequestRepository.findById(100L))
                .thenReturn(
                        Optional.of(
                                VisaRequest.builder()
                                           .id(100L)
                                           .userId("U-100")
                                           .status("processing")
                                           .build()

                        )
                );

        mockMvc.perform(get("/visa/status?ticketId=100"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.status").value("processing"));
    }

    @Test
    void should_return_404_when_ticket_not_found() throws Exception {
        when(visaRequestRepository.findById(101L))
                .thenReturn(
                        Optional.empty()
                );

        mockMvc.perform(get("/visa/status?ticketId=101"))
               .andExpect(status().isNotFound());
    }
}
