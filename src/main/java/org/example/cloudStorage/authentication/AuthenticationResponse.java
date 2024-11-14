package org.example.cloudStorage.authentication;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}
