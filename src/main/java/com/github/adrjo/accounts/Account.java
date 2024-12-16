package com.github.adrjo.accounts;

import com.github.adrjo.users.User;

import java.util.*;

public class Account {
    private int id;
    private String name;
    private String description;
    private int color;

    private User owner;

    private Set<User> users = new HashSet<>();

    public Account(int id, String name, String description, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = new Random().nextInt(0, 16777215);
        this.owner = owner;
        users.add(owner);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getColor() {
        return color;
    }

    public User getOwner() {
        return owner;
    }

    public Set<User> getUsers() {
        return users;
    }
}
