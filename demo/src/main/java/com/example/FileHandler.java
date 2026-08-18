package com.example;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileHandler {

    public void Write(String input) throws IOException {

        LocalDateTime dateTime = LocalDateTime.now();

        DateTimeFormatter formatted = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedPropper = dateTime.format(formatted);

        FileWriter Writer = new FileWriter("systemInfo/Chats.txt", true);
        Writer.write("[" + formattedPropper + "] " + input + "\n");
        Writer.close();

    }

    static String Load(String fileName) {
        String file = null;
        try {
            FileInputStream input = new FileInputStream(fileName);
            file = new String(input.readAllBytes());
            input.close();
        } catch (IOException e) {
            return null;
        }
        return file;
    }
}
