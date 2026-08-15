package com.example;

import java.io.IOException;
import java.util.Collection;
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


        void outputToAll(String msgType, String sender, String content) {  // send to all
            for (MessageHandler handler : users.values()) {

                handler.Builder(msgType, sender, content);
            }
        }

        void outputToMost(String msgType, String sender, String content, String ExeptedUser) {  // send to all users but exepted
            users.forEach((usr, handler) -> {
                if (!usr.equals(ExeptedUser)) {
                    handler.Builder(msgType, sender, content);
                }
            });
        }


}

