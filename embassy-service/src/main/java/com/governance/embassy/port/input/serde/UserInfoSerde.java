package com.governance.embassy.port.input.serde;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.governance.embassy.model.UserInfo;
import lombok.*;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class UserInfoSerde {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    public static class UserInfoWithInput extends UserInfo {
        String input;
    }

    public static class UserInfoDeserializer extends JsonDeserializer<UserInfo> {

        @Override
        public UserInfo deserialize(JsonParser p,
                                    DeserializationContext ctxt) throws IOException, JacksonException
        {
            UserInfoWithInput raw = p.readValueAs(UserInfoWithInput.class);

            if(raw.getLastName() != null && raw.getFirstName() != null) {
                return UserInfo.builder()
                               .firstName(raw.getFirstName())
                               .lastName(raw.getLastName())
                               .build();
            }

            String input = raw.getInput();
            String[] splittedByIs  = input.split("is");

            if (splittedByIs.length == 2) {
                String namesRaw = splittedByIs[1].trim();

                String[] names = namesRaw.split(" ");

                String firstName = names[0];
                String lastName = names[1];
                return UserInfo.builder()
                               .firstName(firstName)
                               .lastName(lastName)
                               .build();
            }

            throw new IllegalArgumentException("Input value is invalid");
        }
    }
}
