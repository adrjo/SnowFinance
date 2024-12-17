package com.github.adrjo.accounts;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.database.Database;
import com.github.adrjo.users.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DatabaseAccountManager implements AccountManager {

    private final Database db;

    private Account activeAccount;

    public DatabaseAccountManager(Database db) {
        this.db = db;
    }

    @Override
    public Account getActiveAccount() {
        return activeAccount;
    }

    @Override
    public void setActiveAccount(Account account) {
        this.activeAccount = account;
    }

    @Override
    public void leave() {
        this.setActiveAccount(null);
    }

    @Override
    public boolean addAccount(Account account) {
        final User user = SnowFinance.instance.getUserManager().getLoggedInUser();

        final String query = """
                                INSERT INTO
                                accounts (name, description, color, owner_id)
                                VALUES (?, ?, ?, ?)
                                RETURNING id
                                """;

        try (PreparedStatement stmt = db.getConnection().prepareStatement(query)) {
            stmt.setString(1, account.getName());
            stmt.setString(2, account.getDescription());
            stmt.setInt(3, account.getColor());
            stmt.setInt(4, user.getId());

            ResultSet set = stmt.executeQuery();
            if (set.next()) {
                int insertedId = set.getInt(1);
                this.addUserToAccount(user.getId(), insertedId);
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Failed to create account: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeAccount(int id) {
        try (PreparedStatement stmt = db.getConnection().prepareStatement("DELETE FROM accounts WHERE id = ?")) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error removing account: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Account getAccount(int id) {
        Set<User> accountUsers = this.getUsersForAccount(id);
        try (PreparedStatement stmt = db.getConnection().prepareStatement("SELECT * FROM acccount WHERE id = ?")) {
            stmt.setInt(1, id);

            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                int accountId = set.getInt(1);
                String name =  set.getString(2);
                String description = set.getString(3);
                int color = set.getInt(4);
                int ownerId = set.getInt(5);

                return new Account(accountId, name, description, color, ownerId, accountUsers);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching account " + e.getMessage());
        }
        return null;
    }

    @Override
    public Set<User> getUsersForAccount(int id) {
        Set<User> users = new HashSet<>();
        try (PreparedStatement stmt = db.getConnection()
                .prepareStatement("""
            SELECT users.id, users.username
            FROM account_users
            JOIN users
            ON users.id = account_users.user_id
            WHERE account_users.account_id = ?
            """)) {

            stmt.setInt(1, id);

            ResultSet set = stmt.executeQuery();


            while (set.next()) {
                int userId = set.getInt(1);
                String userName = set.getString(2);

                users.add(new User(userId, userName));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching account users: " + e.getMessage());
        }

        return users;
    }

    @Override
    public boolean addUserToAccount(int userId, int accountId) {
        try (PreparedStatement stmt = db.getConnection()
                .prepareStatement(
                        """
                                INSERT INTO account_users (user_id, account_id)
                                VALUES(?,?)
                                """)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, accountId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding user to account: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeUserFromAccount(int userId, int accountId) {
        try (PreparedStatement stmt = db.getConnection()
                .prepareStatement(
                        """
                                DELETE FROM account_users
                                WHERE account_id = ? AND user_id = ?
                                """)) {

            stmt.setInt(1, accountId);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error removing user from account: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Set<Account> getAccountsForUser(int id) {
        final Set<Account> accounts = new HashSet<>();

        final String query = """
                        SELECT id, name, description, color, owner_id
                        FROM account_users
                        JOIN accounts ON accounts.id = account_users.account_id
                        WHERE account_users.user_id = ?
                        ORDER BY id ASC
                        """;
        try (PreparedStatement stmt = db.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);

            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                int accountId = set.getInt(1);
                String name = set.getString(2);
                String description = set.getString(3);
                int color = set.getInt(4);
                int ownerId = set.getInt(5);

                final Set<User> users = this.getUsersForAccount(accountId);

                accounts.add(new Account(accountId, name, description, color, ownerId, users));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user accounts: " + e.getMessage());
        }

        return accounts;
    }
}
