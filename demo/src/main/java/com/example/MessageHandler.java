package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;

public class MessageHandler implements Runnable{

    private final InputStream inputStream;

    MessageHandler (Object Input) {
        this.inputStream = (InputStream) Input;
    }
    Gson gson = new Gson();

    Message msg = new Message();
    users usrs = new users();

    @Override
    public void run() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);

                msg = gson.fromJson(line, Message.class);

                String msgType = msg.getMsgType();
                if (msgType.equals("connectionUpdate")) {
                    networkConfirmation();
                } else if (msgType.equals("TextMessage")) {
                    message();
                } else {
                    System.out.println("you fucked up somehow");
                    System.out.println(msgType);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void networkConfirmation() {
        System.out.println("network confirmation");

        String Content = msg.getContent();
        String usr = msg.getUser();

        if (Content.equals("Connecting")) {
            usrs.add(usr);

            System.out.println(usrs.view().toString());
        } else if (Content.equals("Disonnecting")) {
            usrs.remove(usr);

            System.out.println(usrs.view().toString());
        }
    }

    private void message() {
        System.out.println("Message");
    }

    private void create() {

    }
}
