package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import com.google.gson.Gson;

import static java.lang.System.out;

/**
 * Message Handler
 *
 * This is where all inputs into the server are initally handled. a new Handler is called for each user who is connected and saved inside "Users"
 *
 * Two types of messages are send from here being "message" and "network configuration".
 *
 * network configuration is handled separetly because of nessasary action that need to take place when disconnecting. also the messages sent from network configuration are predetermined to can be hard coded in.
 *
 * Message take the information from the message class and convert it into JSON (using googles "gson") which can be docoded in the client, and is sent via the "send" function
 *
 * Builder is used set veriables in the "message" class so that they can be called on in the future. those veriable being user, message type and contence
 *
 * Send simply sends the message but is used to reduce repeating code over the program
 *
 *
 */

public class MessageHandler implements Runnable{

    FileHandler file = new FileHandler();

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

            //usrs.outputToAll("UserList", "Users", String.join(",", usrs.view()));     outputs all usrs to all

            usrs.outputToAll("NewUser", "Users", ("the user " + usr + " has joined"));  //  outputs new user to all


        } else if (Content.equals("Disconnecting")) {
            usrs.remove(usr);
            out.println(usrs.view().toString());
            System.out.println("disconnect reached");
            socket.close();
        }
    }

    private void message() throws IOException {
        usrs.outputToMost("Message", msg.getUser(), msg.getContent(), msg.getUser());  //  outputs new user to all
        String output = String.format("%s: %s", msg.getUser(), msg.getContent());
        file.Write(output);
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
        out.println(FinalMsg + "\n");

        output.write(FinalMsg.getBytes());
        output.flush();
    }
}
