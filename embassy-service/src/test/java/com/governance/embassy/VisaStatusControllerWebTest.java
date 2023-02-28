package com.governance.embassy;

import com.governance.embassy.port.output.HttpClientVisaRequestResponse;
import com.governance.embassy.service.VisaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class VisaStatusControllerWebTest {
    @Autowired MockMvc     mockMvc;
    @MockBean  VisaService visaService;

    @Test
    void should_serialize_with_camel_case() throws Exception {
        //given
        when(visaService.createRequest("U-12")).thenReturn(HttpClientVisaRequestResponse.builder()
                                                                                         .ticketId("T-12")
                                                                                         .build());

        //expect
        mockMvc.perform(get("/visa/request").queryParam(
                       "userId",
                       "U-12"
               ))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.ticketId").value("T-12"));
    }
}
