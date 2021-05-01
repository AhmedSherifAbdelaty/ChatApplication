package org.chatapp;

import org.chatapp.server.ServerWorker;
import org.chatapp.utils.ServerUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) {
        try {
            // listen to any client try to connect to the server with  port 1111
            ServerSocket serverSocket = new ServerSocket(ServerUtils.PORT_NUMBER);
            while (true) {
                System.out.println(ServerUtils.SERVER_READY_MESSAGE);
                // accept any connection to the server
                final Socket clientSocket = serverSocket.accept();
                ServerWorker serverWorker = new ServerWorker(clientSocket);
                serverWorker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
