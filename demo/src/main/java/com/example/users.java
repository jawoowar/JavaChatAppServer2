package com.example;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Users
 *
 * this class is used to hold informatinon about the connected users in a concurrent hash map, avalible no matter the thread it is called from. The hashmap hold the username and a reference to the "MessageHandler" the user uses.
 */

/**
 *
 * Users holds a ConcurrentHashMap of all users and there {@link MessageHandler}s. the functinos inside users are used to manipulate or get data from this hashmap
 *
 * @author Jennifer
 *
 */

public class users {
    /**
     * ConcurrentHashMaps have been chosen because the same list can be accessed from any thread making it best for holding information all threads will need
     */
    private static final ConcurrentHashMap<String, MessageHandler> users = new ConcurrentHashMap<>();

    /**
     * adds new users into the hashmap
     *
     * @param usr username
     * @param handler the {@link MessageHandler} the user is connected to
     * @throws IOException throws if unable to add user and there data
     */
    void add(String usr, MessageHandler handler) throws IOException {
            users.put(usr, handler);

        }

    /**
     * removes a user
     * @param usr the users to be removed
     */

    void remove(String usr) {
            users.remove(usr);
        }

    /**
     * used to see all contence of the HashMap
     * @return returns contence of HashMap
     */

    ConcurrentHashMap.KeySetView<String, MessageHandler> view() {
            return users.keySet();
        }


    /**
     * Sends message to all users
     * @param msgType Type of message
     * @param sender message sender
     * @param content content of message
     */
        void outputToAll(String msgType, String sender, String content) {  // send to all
            for (MessageHandler handler : users.values()) {

                handler.Builder(msgType, sender, content);
            }
        }

    /**
     * sends message to all but exept user
     * @param msgType Type of message
     * @param sender Message sender
     * @param content Message content
     * @param ExeptedUser User who will not recieve the message
     */

    void outputToMost(String msgType, String sender, String content, String ExeptedUser) {  // send to all users but exepted
            users.forEach((usr, handler) -> {
                if (!usr.equals(ExeptedUser)) {
                    handler.Builder(msgType, sender, content);
                }
            });
        }

    /**
     * clears user hashmap
     */

    static void clear() {
            users.clear();
        }

    /**
     * closes each users {@link Message Handler}
     */
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

