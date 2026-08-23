package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * close is used to initiate the closure of the program.
 * if is created on a new thread in {@link Main} to listen for server staff inputs while the server runs
 *
 * @author Jennifer
 *
 */

public class close implements Runnable{

    ServerSocket socket;

    /**
     * this takes the {@code ServerSocket} from {@link Main} so it can be proparly closed later
     *
     * @param socket taked from main so it can be proparly closed later
     */

    close(ServerSocket socket) {
        this.socket = socket;
    }

        Scanner scan = new Scanner(System.in);
        users usrs = new users();

    /**
     * when the nessacery phrase is inputted ("/close") the {@link #close()} function is ran takign the steps to shut down the server
     *
     */
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

    /**
     * takes steps to close the server proparly via functions inside {@link users}
     *
     * @throws IOException throws if unable to complete functino in users
     * @throws InterruptedException throws if unable to close socket
     */

    public void close() throws IOException, InterruptedException {
        System.out.println("Closing");
        usrs.close();
        usrs.clear();
        socket.close();
    }
}
