package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import com.google.gson.Gson;

import static java.lang.System.out;

public class MessageHandler implements Runnable{

    private final InputStream inputStream;
    private final Socket socket;

    MessageHandler (Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.output = socket.getOutputStream();
    }

    private Gson gson = new Gson();
    private Message msg = new Message();
    private users usrs = new users();
    private OutputStream output;

    @Override
    public void run() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = in.readLine()) != null) {
                out.println(line);

                msg = gson.fromJson(line, Message.class);

                String msgType = msg.getMsgType();
                if (msgType.equals("connectionUpdate")) {
                    networkConfirmation();
                } else if (msgType.equals("TextMessage")) {
                    message();
                } else {
                    out.println("you fucked up somehow");
                    out.println(msgType);
                }
            }

        } catch (IOException e) {
            if (!socket.isClosed()) {
                throw new RuntimeException(e);
            }

        }

    }

    private void networkConfirmation() throws IOException {
        out.println("network confirmation");

        String Content = msg.getContent();
        String usr = msg.getUser();

        if (Content.equals("Connecting")) {
            usrs.add(usr, this);
            out.println(usrs.view().toString());

            Builder("Confirmation", "Server", "Connected");
            Builder("UserList", "Users", String.join(",", usrs.view()));


        } else if (Content.equals("Disconnecting")) {
            usrs.remove(usr);
            out.println(usrs.view().toString());
            System.out.println("disconnect reached");
            socket.close();
        }
    }

    private void message() {
        out.println("Message");
    }

    void Builder(String MsgType, String User, String txt) {

        Message sendMsg = new Message();

        sendMsg.setMessageType(MsgType);
        sendMsg.setSender(User);
        sendMsg.setContent(txt);

        Gson gson = new Gson();

        String json = gson.toJson(sendMsg);

        String FinalMsg = json + "\n";

        try {
            send(FinalMsg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void send(String Message) throws IOException {
        String FinalMsg = Message;

        out.println("send reached");
        out.println(FinalMsg);

        output.write(FinalMsg.getBytes());
        output.flush();
    }
}
