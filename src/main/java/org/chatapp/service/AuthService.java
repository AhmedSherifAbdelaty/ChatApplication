package org.chatapp.service;

import org.chatapp.repository.AuthRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;

public class AuthService {

    AuthRepository authRepository;
    BCryptPasswordEncoder passwordEncoder;

    public AuthService() {
        this.authRepository = new AuthRepository();
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean handleLogin(String username, String password) throws IOException {
        return authRepository.getUserByUsernameAndPassword(username, password);
    }
}
