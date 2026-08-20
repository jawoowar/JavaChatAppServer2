package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class Website implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream out = exchange.getResponseBody();

        StringBuilder builder = new StringBuilder();
        users usrs = new users();


        String UsrListStr = String.valueOf(usrs.view());
        String UsrCount = String.valueOf(usrs.view().size());

        Runtime runtime = Runtime.getRuntime();

        long maxMemory = ((runtime.maxMemory()/ 1024) / 1024);
        long totalMemory = ((runtime.totalMemory()/ 1024) / 1024);
        long freeMemory = ((runtime.freeMemory()/ 1024) / 1024);
        int freeProcesses = runtime.availableProcessors();


        String pagePath = exchange.getRequestURI().getPath();
        System.out.println(pagePath);


        String file = FileHandler.Load("demo/src/main/resources/index.html");

        file = file.replace("{users}", UsrListStr);
        file = file.replace("{userCount}", UsrCount);
        file = file.replace("{ramUsage}", String.valueOf(totalMemory));
        file = file.replace("{totalRam}", String.valueOf(freeMemory));
        file = file.replace("{totalCPU}", String.valueOf(freeProcesses));

        if (file != null) {
            byte[] responseBytes = file.getBytes("UTF-8");
            exchange.sendResponseHeaders(200, responseBytes.length);
            out.write(responseBytes);
            out.flush();
            out.close();
        } else {
            exchange.sendResponseHeaders(404, 0);
        }


    }
}
