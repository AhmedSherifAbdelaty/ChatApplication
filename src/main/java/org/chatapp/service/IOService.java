package org.chatapp.service;

import java.io.*;
import java.net.Socket;

public class IOService {

    //get input from client
    private  InputStream  inputStream;
    // send output to client
    private  DataOutputStream  outputStream ;
    private BufferedReader reader;
    private Socket clientSocket ;

    public IOService (Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        inputStream= clientSocket.getInputStream();
        outputStream= new DataOutputStream(clientSocket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }


    public void sendMessageToUser(String message) throws IOException {
        outputStream.writeUTF(message);
    }

    public String receiveMessageFromUser() throws IOException {
        return reader.readLine().replaceAll(" +", " ");
    }

    public String getUserInput(String message) throws IOException {
        sendMessageToUser(message);
        return receiveMessageFromUser();
    }
}
