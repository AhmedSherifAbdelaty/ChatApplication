package org.chatapp.repository;

import org.chatapp.fileHandler.FileHandler;

import java.io.IOException;

public class AuthRepository {

    public boolean getUserByUsernameAndPassword(String username, String password) throws IOException {
        return FileHandler.authenticationFromFile(username, password);
    }
}
