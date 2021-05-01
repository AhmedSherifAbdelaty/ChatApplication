package org.chatapp.fileHandler;

import org.chatapp.utils.DatabaseUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class FileHandler {

    public static final String filePath  = System.getProperty("user.dir")+"\\src\\main\\resources\\" ;

    public static boolean authenticationFromFile(String user, String pass) throws IOException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        File file = new File(filePath + DatabaseUtils.DATABASE_NAME);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        while ((st = br.readLine()) != null) {
            String[] arr = st.split("& ");
            String username = arr[0].replaceAll(DatabaseUtils.USERNAME_STRUCTURE, "").trim();
            String password = arr[1].replaceAll(DatabaseUtils.PASSWORD_STRUCTURE, "");
            if (passwordEncoder.matches(pass, password) && user.equals(username)) return true;
        }
        return false;
    }

    public static void write(String filePath, String data) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            if (file.isDirectory()) { //conv/ahmed/0.txt
                file.mkdirs();
            } else {
                file.getParentFile().mkdirs();
            }
        }
        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        output.append(data);
        output.append("\r\n");
        output.close();
    }

    public static String read(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder();
        File file = new File(filePath);
        if (file.exists()) {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                fileData.append(myReader.nextLine());
                fileData.append("\r\n");
            }
            myReader.close();
        }
       return fileData.toString();
    }


    public static int getDirectoryFilesCount(String directoryPath) {
        int fileListCount = 0;
        File file = new File(directoryPath);

        if (file.isDirectory()) {
            fileListCount = Objects.requireNonNull(file.list()).length;
        }

        return fileListCount;
    }
}
