package com.example;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class users {
    private static final ConcurrentHashMap<String, MessageHandler> users = new ConcurrentHashMap<>();

        void add(String usr, MessageHandler handler) throws IOException {
            users.put(usr, handler);

        }

        void remove(String usr) {
            users.remove(usr);
        }

        ConcurrentHashMap.KeySetView<String, MessageHandler> view() {
            return users.keySet();
        }
}

