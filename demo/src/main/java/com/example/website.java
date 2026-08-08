package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class website implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream out = exchange.getResponseBody();

        StringBuilder builder = new StringBuilder();

        builder.append("<html><body>");
        builder.append("<h1>Hello World</h1>");
        builder.append("</body></html>");

        byte[] responseBytes = builder.toString().getBytes("UTF-8");
        exchange.sendResponseHeaders(200, responseBytes.length);
        out.write(responseBytes);
        out.flush();
        out.close();
    }
}
