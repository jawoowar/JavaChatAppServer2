package com.example;

import java.io.File;
import java.io.IOException;

/**
 * StartUp
 *
 * this class is used to confirm all nessasary files are created and avalible to use later down the line
 */

public class startUp {

    public void CheckFile() {
        File con = new File("systemInfo/Chats.txt");

        if (con.exists()) {
            System.out.println("file exists: True");
        } else {
            System.out.println("file exists: False");
            createFile();
        }

    }

    public void createFile() {
        File con = new File("systemInfo/Chats.txt");

        try {
            if (con.createNewFile()) {
                System.out.println("file created");
            } else {
                System.out.println("failed to create file");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void CheckDict() throws IOException {
        File con = new File("systemInfo/");

        if (con.exists()) {
            System.out.println("directory Exists: True");
            CheckFile();
        } else {
            System.out.println("directory Exists: False");
            CreateDirectory("systemInfo/");
        }

    }

    public void CreateDirectory(String dicName) {
        File dic = new File(dicName);

        boolean dicCreated = dic.mkdir();
        if (dicCreated) {
            System.out.println("Directory created");
            CheckFile();
        } else {
            System.out.println("Unsuccessful in directory creation");
        }
    }


}
