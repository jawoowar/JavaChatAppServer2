package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class close implements Runnable{

    ServerSocket socket;

    close(ServerSocket socket) {
        this.socket = socket;
    }

    // get server port from main create new server port and use in message handler then close when done

        Scanner scan = new Scanner(System.in);
        users usrs = new users();

        @Override
        public void run() {
            while (true) {
                String msg = scan.nextLine();
                if (msg.equals("/close")) {
                    try {
                        close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }



    public void close() throws IOException, InterruptedException {
        System.out.println("Closing");
        usrs.close();
        usrs.clear();
        socket.close();
    }
}
