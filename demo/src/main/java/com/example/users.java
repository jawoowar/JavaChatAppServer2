package com.example;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Users
 *
 * this class is used to hold informatinon about the connected users in a concurrent hash map, avalible no matter the thread it is called from. The hashmap hold the username and a reference to the "MessageHandler" the user uses.
 */

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

        static void clear() {
            users.clear();
        }

        static void close() {
            for (MessageHandler handler : users.values()) {

                try {
                    handler.ServerDisconnect();
                    handler.closer();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


}

