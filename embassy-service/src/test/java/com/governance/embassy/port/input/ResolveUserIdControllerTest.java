package com.governance.embassy.port.input;

import com.governance.embassy.model.UserInfo;
import com.governance.embassy.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
public class ResolveUserIdControllerTest {
    @Autowired MockMvc     mockMvc;
    @MockBean  UserService service;

    @Test
    void should_return_user_id_by_names() throws Exception {
        //given
        when(service.resolveUserId(any(UserInfo.class))).thenReturn("U-111");

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
               .andExpect(jsonPath("$.userId").value("U-111"));
    }
}
