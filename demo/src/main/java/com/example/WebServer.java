package com.example;

import com.sun.net.httpserver.HttpServer;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer implements Runnable{
    private int port;

    WebServer(int port) {
        this.port = port;
    }

    public void run() {

        // thread pool creation
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            e.printStackTrace();
        });

        try {


            // creates https server + sets socket and https url
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", port), 0);

            server.setExecutor(threadPool);
            server.start();
            System.out.println("Server started on port " + port);


            server.createContext("/", new Website());


        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
