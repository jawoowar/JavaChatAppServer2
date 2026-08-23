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

    FileHandler file = new FileHandler();

    private final InputStream inputStream;
    private final Socket socket;

    /**
     *
     * the main functions are brought in those being {@code InputStream}, {@code Socket}, {@code Gson}, {@code Message}, {@code users}, and {@code OutpurStream}.
     * <p>
     *     it begins with {@link run()} which opens up the InputStream, sets relevent veriables in {@link Message}, then calls relevent functions based on the {@code msgTyoe}.
     * </p>
     *
     * @param socket handed into this function after being set in {@link Main}, then {@code getInputStream()} and {@code getOutputStream()} streams are created from it
     * @throws IOException thrown if the socket is closed
     * @author Jennifer
     */

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

    /**
     *
     * gets the {@code Content} and {@code usr} from and depending on the "content" chooses between "connecting" and "Disconnecting".
     *
     * @throws IOException throws if socket doesnt close
     */

    private void networkConfirmation() throws IOException {
        out.println("network confirmation");

        String Content = msg.getContent();
        String usr = msg.getUser();

        /**
         * adds usr and their {@link MessageHandler} to the hashmap inside {@link users}. then sends a confirmation message to the user connecting and new user message to all other connected users
         */

        if (Content.equals("Connecting")) {
            usrs.add(usr, this);
            out.println(usrs.view().toString());

            Builder("Confirmation", "Server", "Connected");

            Builder("UserList", "Users", String.join(",", usrs.view()));

            //usrs.outputToAll("UserList", "Users", String.join(",", usrs.view()));     outputs all usrs to all

            usrs.outputToAll("NewUser", "Users", ("the user " + usr + " has joined"));  //  outputs new user to all


            /**
             *
             * removes user from the hashmap inside {@link users} and closes the {@code socket}
             *
             */

        } else if (Content.equals("Disconnecting")) {
            usrs.remove(usr);
            out.println(usrs.view().toString());
            System.out.println("disconnect reached");
            socket.close();
        }
    }

    /**
     *
     * sends message to all users other then the one sending it via the {@link users#outputToMost(String, String, String, String)} passing over the MsgType "Message" and the stored information inside {@link Message}.
     * <p>
     *     also writes to a "chatlog" via {@link FileHandler#Write(String)}
     * </p>
     * @throws IOException throws if unable to write to file
     */

    private void message() throws IOException {
        usrs.outputToMost("Message", msg.getUser(), msg.getContent(), msg.getUser());  //  outputs new user to all but the sending user
        String output = String.format("%s: %s", msg.getUser(), msg.getContent());
        file.Write(output);
    }

    /**
     *
     * Sets information inside the middle man {@link Message}, then formats the information into JSON using {@code gson} to then be send to the relevent user via {@link send}
     *
     * @param MsgType Type of message sending so it can be proparly interpreted on the clients side
     * @param User The user sending the message
     * @param txt the contence of the message
     */

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

    /**
     * This sends the message to the user using the output Stream spesified in {@link MessageHandler}
     *
     * @param Message the message beind sendk build in {@link #Builder(String, String, String)}
     * @throws IOException throws if unable to write or flush socket
     */

    private void send(String Message) throws IOException {
        String FinalMsg = Message;

        out.println("send reached");
        out.println(FinalMsg + "\n");

        output.write(FinalMsg.getBytes());
        output.flush();
    }

    /**
     * sends out a messsage to all connected clients saying the server is closed using the {@link #Builder(String, String, String)} function
     */

    public void ServerDisconnect(){
        Builder("ConnectionStatus", "Server", "Server Closed");
        System.out.println("disconnect message Sent");

    }

    /**
     * closes inputStream, outputStream and socket
     *
     * @throws IOException throws if unable to close any listen element
     */

    public void closer() throws IOException {
        inputStream.close();
        output.close();
        socket.close();
    }
}
