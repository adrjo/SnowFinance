package com.github.adrjo.users;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.accounts.Account;
import com.github.adrjo.database.Database;
import com.github.adrjo.util.HashHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
                                     RETURNING id
                                     """)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getHashedPassword());

            ResultSet set = stmt.executeQuery();
            if (set.next()) {
                int insertedId = set.getInt(1);
                Account mainAccount = new Account("Main Account", user.getName() + "'s Main Account");
                SnowFinance.instance.getAccountManager().addAccount(insertedId, mainAccount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }

    @Override
    public boolean removeUser(int id) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement("DELETE FROM users WHERE id = ?")) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }

    @Override
    public User getUser(int id) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement("SELECT * FROM users WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet set = stmt.executeQuery();

            return getUserResult(set);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }

    @Override
    public User getUser(String username) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement("SELECT * FROM users WHERE LOWER(username) = ?")) {
            stmt.setString(1, username.toLowerCase());
            ResultSet set = stmt.executeQuery();

            return getUserResult(set);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }

    private User getUserResult(ResultSet set) throws SQLException {
        if (set.next()) {
            int id = set.getInt(1);
            String name = set.getString(2);
            String hashedPass = set.getString(3);

            return new User(id, name, hashedPass);
        }
        return null;
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

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement stmt = db.getConnection().createStatement()) {
            ResultSet set = stmt.executeQuery("SELECT id, username FROM users");

            while (set.next()) {
                int id = set.getInt(1);
                String name = set.getString(2);

                users.add(new User(id, name));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
        return users;
    }
}
