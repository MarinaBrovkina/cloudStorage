package org.example.cloudStorage.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseJWT {

    @JsonProperty("auth-token")
    private final String authToken;
}
