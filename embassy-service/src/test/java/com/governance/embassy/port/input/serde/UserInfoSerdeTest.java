package com.governance.embassy.port.input.serde;

import com.governance.embassy.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JsonTest
public class UserInfoSerdeTest {
    @Autowired JacksonTester<UserInfo> userInfoJacksonTester;

    @Test
    void should_parse_input_field_into_user_info_names_object() throws IOException {
        ObjectContent<UserInfo> tester = userInfoJacksonTester.parse(
                //language=json
                """
                        {"input": "My name is vasya pupkin"} 
                        """);

        tester.assertThat()
              .hasFieldOrPropertyWithValue(
                      "firstName",
                      "vasya"
              )
              .hasFieldOrPropertyWithValue(
                      "lastName",
                      "pupkin"
              );
    }

    @Test
    void should_parse_user_info_for_expected_names() throws IOException {
        ObjectContent<UserInfo> tester = userInfoJacksonTester.parse(
                //language=json
                """
                        {"firstName": "vasya", "lastName":  "pupkin"} 
                        """);

        tester.assertThat()
              .hasFieldOrPropertyWithValue(
                      "firstName",
                      "vasya"
              )
              .hasFieldOrPropertyWithValue(
                      "lastName",
                      "pupkin"
              );
    }


    @Test
    void should_throw_when_input_is_invalid() throws IOException {
        //when
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    userInfoJacksonTester.parse(
                            //language=json
                            """
                                    {"input": "Im a god"}
                                    """);

                }
        );


        //then
        assertThat(exception.getMessage()).isEqualTo("Input value is invalid");
    }
}
