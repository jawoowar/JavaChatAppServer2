package com.example;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Java Chat App
 * @author Jennifer Woodward
 *
 * This project is a java chat app with a chat logs and a locally hosted web server to monitor connected users and system information.
 *
 */

/**
 * in the main class the program all nessacery information being website port and server port. then when running starts the webserver and a new threads when a new user connects
 */

public class Main {
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

            while (running) {
                Socket clientSocket = socket.accept();
                System.out.println("socket accepted");

                MessageHandler msgHandle = new MessageHandler(clientSocket);

                System.out.print(clientSocket.getInputStream());

                new Thread(msgHandle).start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}