package com.github.adrjo.users;

import com.github.adrjo.util.HashHelper;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String hashedPassword;

    public User(int id, String name, String hashedPassword) {
        this.id = id;
        this.name = name;
        this.hashedPassword = hashedPassword;
    }

    public User(String username, String password) {
        this.name = username;
        this.hashedPassword = HashHelper.hashAndSaltPassword(password);
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
