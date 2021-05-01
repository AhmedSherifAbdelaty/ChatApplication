package org.chatapp.repository;

import org.chatapp.fileHandler.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatRepository {

    private static final String FILE_PATH = System.getProperty("user.dir")+"\\src\\main\\resources\\";
    private static final String DIR_PATH  = FILE_PATH + "Conversations\\";
    public static final String FILE_EXTENSION = ".txt";

    public void createNewChat(String username, String chat) throws IOException {
        String userPathData = DIR_PATH + username; // full path of user
        int chatsCount = FileHandler.getDirectoryFilesCount(userPathData);
        String newChatFileName = String.valueOf(chatsCount);
        String newChatFilePath = userPathData + File.separator + newChatFileName + FILE_EXTENSION;
        FileHandler.write(newChatFilePath, chat);
    }

    public String getLastChat(String username) throws IOException {
        String chatData = "";
        String userPathData = DIR_PATH + username;
        int chatsCount = FileHandler.getDirectoryFilesCount(userPathData);
        if (chatsCount != 0) {
            String lastChatFileName = String.valueOf(chatsCount - 1);
            String lastChatFilePath = userPathData + File.separator + lastChatFileName + FILE_EXTENSION;
            chatData = FileHandler.read(lastChatFilePath);
        }
        return chatData;
    }

    public List<String> getAllChats(String username) throws IOException {
        List<String> chatDataList = new ArrayList<>();
        String userPathData = DIR_PATH + username;
        int chatsCount = FileHandler.getDirectoryFilesCount(userPathData);
        for (int i = 0; i < chatsCount; i++) {
            String chatFileName = String.valueOf(i);
            String chatFilePath = userPathData + File.separator + chatFileName + FILE_EXTENSION;
            chatDataList.add(FileHandler.read(chatFilePath));
        }
        return chatDataList;
    }
}
