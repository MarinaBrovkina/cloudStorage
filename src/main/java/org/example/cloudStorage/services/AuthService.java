package org.example.cloudStorage.services;

import org.example.cloudStorage.entity.User;
import org.example.cloudStorage.exceptions.ErrorBadCredentials;
import org.example.cloudStorage.jwt.JWTUtils;
import org.example.cloudStorage.models.request.RequestAuth;
import org.example.cloudStorage.models.response.ResponseJWT;
import org.example.cloudStorage.repositories.AuthRepository;
import org.example.cloudStorage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, AuthRepository authRepository, JWTUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public ResponseJWT login(RequestAuth requestAuth) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestAuth.getLogin(), requestAuth.getPassword()));
        } catch (ErrorBadCredentials e) {
            throw new ErrorBadCredentials();
        }
        User user = userRepository.findUserByLogin(requestAuth.getLogin());
        String token = jwtUtils.generateToken(user);
        authRepository.saveAuthenticationUser(token, user);
        return new ResponseJWT(token);
    }


    public void logout(String authToken) {
        String jwt = authToken.substring(7);
        authRepository.deleteAuthenticationUserByToken(jwt);
    }
}