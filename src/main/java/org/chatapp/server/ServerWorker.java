package org.chatapp.server;

import java.io.*;
import java.net.Socket;

import org.chatapp.service.AuthService;
import org.chatapp.service.ChatService;
import org.chatapp.service.IOService;
import org.chatapp.utils.ClientUtils;

public class ServerWorker extends Thread {

    private String newLine = System.getProperty("line.separator");
    private final Socket clientSocket;
    private final AuthService authService;
    private final ChatService chatService;
    private final IOService ioService;

    public ServerWorker(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.authService = new AuthService();
        this.chatService = new ChatService();
        this.ioService = new IOService(clientSocket);
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException {
        String line = "";
        String username;
        String password;

        while (true) {
            username = ioService.getUserInput(ClientUtils.ENTER_USERNAME);
            password = ioService.getUserInput(ClientUtils.ENTER_PASSWORD);
            boolean isAuth = authService.handleLogin(username, password);
            if (isAuth) {
                chatMethod(username);
                line = ioService.getUserInput(ClientUtils.ASK_TO_TERMINATE);
                if (ClientUtils.TERMINATE_CONN.equals(line)) {
                    break;
                }
            } else {
                ioService.sendMessageToUser(ClientUtils.INVALID_USER_OR_PASSWORD + newLine);
            }
        }
        clientSocket.close();
    }


    private void chatMethod(String user) {
        String chat = "";
        try {
            ioService.sendMessageToUser(ClientUtils.LOGIN + newLine);
            String line = ioService.receiveMessageFromUser();
            while (line != null) {
                if (ClientUtils.ENDING_CONV.equals(line)) {
                    ioService.sendMessageToUser(ClientUtils.LOGOUT + newLine);
                    if (chat.length() > 1 && chat.replaceAll("\\s", "").length() > 0) {
                        chatService.saveChat(user, chat);
                    }
                    System.out.println(ClientUtils.LAST_OCCURRENCE + user + chatService.countWordsInLastChat(user));
                    System.out.println(ClientUtils.ALL_OCCURRENCE + user + chatService.countWordsInAllChats(user));
                    System.out.println();
                    break;
                } else {
                    ioService.sendMessageToUser(ClientUtils.SENDING_MESSAGES + newLine);
                    chat += line + "\n";
                }
                line = ioService.receiveMessageFromUser();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

