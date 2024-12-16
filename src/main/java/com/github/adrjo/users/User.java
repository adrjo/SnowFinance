package com.github.adrjo.users;

import com.github.adrjo.util.HashHelper;

public class User {
    private int id;
    private String name;
    private String hashedPassword;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String username, String password) {
        this.name = username;
        this.hashedPassword = HashHelper.hashAndSaltPassword(password);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
