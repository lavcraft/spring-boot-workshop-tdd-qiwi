package com.governance.embassy.port.input;

import com.governance.embassy.model.UserInfo;
import com.governance.embassy.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ResolveUserIdControllerTest {
    @Autowired MockMvc                  mockMvc;
    @MockBean  UserService              service;

    @Test
    void should_return_user_id_by_input_string() throws Exception {
        //given
        when(service.resolveUserId(
                UserInfo.builder()
                        .firstName("Vasya")
                        .lastName("Pupkin")
                        .build()))
                .thenReturn("U-111");

        //expect
        mockMvc.perform(post("/user/resolve-id")
                                //language=json
                                .content("""
                                        {
                                        "input": "my name is Vasya Pupkin"
                                        }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
               )
                .andExpect(status().isOk())
               .andExpect(jsonPath("$.userId").value("U-111"));
    }

    @Test
    void should_return_user_id_by_names() throws Exception {
        //given
        when(service.resolveUserId(
                UserInfo.builder()
                        .firstName("Vasya")
                        .lastName("Pupkinzev")
                        .build()
        ))
                .thenReturn("U-111");

        //expect
        mockMvc.perform(post("/user/resolve-id")
                                //language=json
                                .content("""
                                        { "firstName": "Vasya", "lastName": "Pupkinzev" }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.userId").value("U-111"));
    }

    @Test
    void should_return_error_when_input_is_invalid() throws Exception {
        //given
        when(service.resolveUserId(
                UserInfo.builder()
                        .firstName("Vasya")
                        .lastName("Pupkin")
                        .build()))
                .thenReturn("U-111");

        //expect
        mockMvc.perform(post("/user/resolve-id")
                                //language=json
                                .content("""
                                        { "input": "Abrajadabra" }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
               )
               .andExpect(status().is4xxClientError())
               .andExpect(jsonPath("$.status").value("invalid input"));
    }
}
