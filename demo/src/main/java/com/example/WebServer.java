package com.example;

import com.sun.net.httpserver.HttpServer;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;


/**
 * used to initiate webserver port and open the website on local host
 *
 * @author Jennifer
 */
public class WebServer implements Runnable{
    private int port;

    WebServer(int port) {
        this.port = port;
    }

    /**
     * created Thead pool
     */

    public void run() {

        // thread pool creation
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            e.printStackTrace();
        });

        /**
         * creates server and Website from given port on localhost
         *
         * @throws IOException if unable to create server or website
         */
        try {


            // creates https server + sets socket and https url
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", port), 0);

            server.setExecutor(threadPool);
            server.start();
            out.println("Server started on port " + port);


            server.createContext("/", new Website());

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
