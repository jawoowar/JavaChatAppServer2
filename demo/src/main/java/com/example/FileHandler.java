package com.example;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * Writes to chat logs and reads from web display files
 * @author Jennifer
 *
 */

public class FileHandler {

    /**
     * Only used for writing to Chatlogs so holds all formatting for it inside
     * <p>
     *     formats {@code input} by putting the current time are the start, the prior formatted message and putting a line break on the end.
     *     uses {@code Writer} to write to files nad {@code LocalDateTIme} to handle gathering the current date and time
     * </p>
     *
     * @param input the message to the saved
     * @throws IOException throws when unable to write to file
     */

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
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            return null;
        }
        catch (IOException e) {
            return null;
        }
        return file;
    }
}
