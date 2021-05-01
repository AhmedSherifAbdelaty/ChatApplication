package org.chatapp.service;

import org.chatapp.repository.ChatRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService() {
        chatRepository = new ChatRepository();
    }

    public void saveChat(String username, String chat) throws IOException {
        chatRepository.createNewChat(username, chat);
    }

    public Map<String, Integer> countWordsInLastChat(String username) throws IOException {
        String chat = chatRepository.getLastChat(username);
        Map<String, Integer> map = new HashMap<>();
        String[] words = chat.split("\\s+"); // split by multiple white space
        for (String word : words) {
            if (word.length() > 0) {
                if (map.containsKey(word)) { //already exists
                    map.put(word, map.get(word) + 1); // increase value by 1
                } else {
                    map.put(word, 1); // first occurrence
                }
            }
        }
        return map;
    }

    public Map<String, Integer> countWordsInAllChats(String username) throws IOException {
        List<String> chatsList = chatRepository.getAllChats(username);
        Map<String, Integer> map = new HashMap<>();
        for (String chat : chatsList) {
            String[] words = chat.split("\\s+"); // split by multiple white space
            for (String word : words) {
                if (word.length() > 0) {
                    if (map.containsKey(word)) { //already exists
                        map.put(word, map.get(word) + 1); // increase value by 1
                    } else {
                        map.put(word, 1); // first occurrence
                    }
                }
            }
        }
        return map;
    }
}
