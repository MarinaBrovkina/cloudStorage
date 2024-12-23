package org.example.cloudStorage.controllers;

import jakarta.validation.Valid;
import org.example.cloudStorage.models.request.RequestAuth;
import org.example.cloudStorage.models.response.ResponseJWT;
import org.example.cloudStorage.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody RequestAuth requestAuth) {
        ResponseJWT token = authService.login(requestAuth);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken) {
        authService.logout(authToken);
        return new ResponseEntity<>("Success logout", HttpStatus.OK);
    }

}
