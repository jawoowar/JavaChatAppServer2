package com.example;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

/* TO DO
    - Docs on Users, Webserver and Website
    - CSS working on website
    - refreshing on website
    - way to end server when its running
 */


/**
 * Java Chat App
 * @author Jennifer
 *
 * This project is a java chat app with a chat logs and a locally hosted web server to monitor connected users and system information.
 *
 */

public class Main {

    /**
     * main is used for initiating the {@link startUp} and gathering nessacery information about the server, (webport and socket port).
     * <p>
     *     it also handles creation of new threads when a new user join and handing them over to {@link MessageHandler}
     * </p>
     * <p>
     *     the webserver is also started here via {@code WebServer} using {@link WebServer}
     * </p>
     *
     * @param args unused
     * @throws IOException throws when unable to start a new thread
     */
    public static void main(String[] args) throws IOException {

        startUp su = new startUp();

        Scanner scan = new Scanner(System.in);

        System.out.println("Input Website Socket port: ");
        int WebsitePort = Integer.parseInt(scan.nextLine());

        System.out.println("Input Server Socket port: ");
        int ServerPort = Integer.parseInt(scan.nextLine());

        Boolean status = Boolean.TRUE;

        while (status == Boolean.TRUE) {
            System.out.println("Type 'start' to start chat server: ");
            String input = scan.nextLine();
            if (input.equals("start")) {
                System.out.println("Server started");
                su.CheckDict();
                status = Boolean.FALSE;
            } else {
                System.out.println("command not recognised");
            }
        }

        new Thread(new WebServer(WebsitePort)).start();
        System.out.println("Web server opened");




        try {
            ServerSocket socket = new ServerSocket(ServerPort);
            boolean running = true;

            close close = new close(socket);

            new Thread(close).start();

            while (running) {
                try {
                    Socket clientSocket = socket.accept();
                    System.out.println("socket accepted");

                    MessageHandler msgHandle = new MessageHandler(clientSocket);

                    System.out.print(clientSocket.getInputStream());

                    new Thread(msgHandle).start();
                } catch (IOException e) {
                    if (socket.isClosed()) {
                        System.out.println("server closed");
                        running = false;
                    } else {
                        throw new RuntimeException(e);
                    }
                }


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



}