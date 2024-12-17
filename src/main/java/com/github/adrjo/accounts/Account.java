package com.github.adrjo.accounts;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.users.User;

import java.util.*;

public class Account {
    private int id;
    private String name;
    private String description;
    private int color;

    private int ownerId;

    private Set<User> users = new HashSet<>();

    public Account(String name, String description) {
        this.name = name;
        this.description = description;
        this.color = new Random().nextInt(0, 255);
    }

    public Account(int id, String name, String description, int color, int ownerId, Set<User> users) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.ownerId = ownerId;
        this.users = users;
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

    public Set<User> getUsers() {
        return users;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public boolean isOwner() {
        final User user = SnowFinance.instance.getUserManager().getLoggedInUser();
        return user != null && user.getId() == this.ownerId;
    }
}
