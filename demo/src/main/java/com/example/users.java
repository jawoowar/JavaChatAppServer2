package com.example;

import java.util.ArrayList;

public class users {
    ArrayList<String> users = new ArrayList<String>();

    void add(String usr) {
        users.add(usr);
    }

    void remove(String usr) {
        users.remove(usr);
    }

    ArrayList view() {
        return users;
    }
}
