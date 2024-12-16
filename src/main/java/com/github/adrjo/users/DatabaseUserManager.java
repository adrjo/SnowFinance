package com.github.adrjo.users;

import com.github.adrjo.accounts.Account;
import com.github.adrjo.database.Database;
import com.github.adrjo.util.HashHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUserManager implements UserManager {
    private final Database db;
    private User loggedInUser;

    public DatabaseUserManager(Database db) {
        this.db = db;
    }

    @Override
    public User getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    @Override
    public void addUser(User user) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement(
                             """
                                     INSERT INTO
                                     users (username, hashed_password)
                                     VALUES (?, ?)
                                     """)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getHashedPassword());

            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }

    @Override
    public void removeUser(int id) {

    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public User getUser(String username) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement("SELECT * FROM users WHERE LOWER(username) = ?")) {
            stmt.setString(1, username.toLowerCase());

            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                int id = set.getInt(1);
                String name = set.getString(2);
                String hashedPass = set.getString(3);

                return new User(id, name, hashedPass);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }

    @Override
    public boolean userExists(String username) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement("SELECT 1 FROM users WHERE LOWER(username) = ?")) {
            stmt.setString(1, username.toLowerCase());

            return stmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }

    @Override
    public Account getAccountsForUser(int id) {
        return null;
    }

    @Override
    public boolean login(String username, String password) {
        final User user = this.getUser(username);
        if (HashHelper.verifyHash(password, user.getHashedPassword())) {
            this.setLoggedInUser(user);
            return true;
        }
        return false;
    }

    @Override
    public void logout() {
        this.setLoggedInUser(null);
    }
}
