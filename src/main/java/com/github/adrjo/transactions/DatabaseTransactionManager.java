package com.github.adrjo.transactions;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.accounts.Account;
import com.github.adrjo.database.Database;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class DatabaseTransactionManager implements TransactionManager {

    private final Database db;

    public DatabaseTransactionManager(Database db) {
        this.db = db;
    }

    @Override
    public void load() {
        // no-op
    }

    @Override
    public void save() {
        // no-op
    }

    @Override
    public boolean add(Transaction transaction) {
        final Account account = SnowFinance.instance.getAccountManager().getActiveAccount();

        try (PreparedStatement stmt = db.getConnection()
                .prepareStatement("""
                        INSERT INTO transactions (description, amount, timestamp_ms, account_id)
                        VALUES (?, ?, ?, ?)
                        """)) {
            stmt.setString(1, transaction.description());
            stmt.setBigDecimal(2, BigDecimal.valueOf(transaction.amount()));
            stmt.setLong(3, transaction.timestamp());
            stmt.setInt(4, account.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding transaction: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean add(int id, Transaction transaction) {
        final Account account = SnowFinance.instance.getAccountManager().getActiveAccount();

        try (PreparedStatement stmt = db.getConnection()
                .prepareStatement("""
                        INSERT INTO transactions (id, description, amount, timestamp_ms, account_id)
                        VALUES (?, ?, ?, ?, ?)
                        """)) {
            stmt.setInt(1, id);
            stmt.setString(2, transaction.description());
            stmt.setBigDecimal(3, BigDecimal.valueOf(transaction.amount()));
            stmt.setLong(4, transaction.timestamp());
            stmt.setInt(5, account.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding transaction: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean remove(int id) {
        try (PreparedStatement stmt = db.getConnection()
                .prepareStatement("DELETE FROM transactions WHERE id = ?")) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error removing transaction: " + e.getMessage());
        }

        return false;
    }

    @Override
    public Transaction get(int id) {
        try (PreparedStatement stmt = db.getConnection()
                .prepareStatement("SELECT description, amount, timestamp_ms FROM transactions WHERE id = ?")) {
            stmt.setInt(1, id);

            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                String desc = set.getString(1);
                BigDecimal amount = set.getBigDecimal(2);
                long timestamp = set.getLong(3);

                return new Transaction(desc, amount.doubleValue(), timestamp);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching transaction: " + e.getMessage());
        }
        return null;
    }

    @Override
    public double getBalanceAt(long timestamp) {
        final Account account = SnowFinance.instance.getAccountManager().getActiveAccount();

        final String query = """
                        SELECT amount
                        FROM transactions
                        WHERE
                        account_id = ?
                        AND
                        timestamp_ms <= ?
                        """;
        try (PreparedStatement stmt = db.getConnection().prepareStatement(query)) {
            stmt.setInt(1, account.getId());
            stmt.setLong(2, timestamp);

            ResultSet set = stmt.executeQuery();
            BigDecimal total = BigDecimal.ZERO;
            while (set.next()) {
                BigDecimal amount = set.getBigDecimal(1);
                total = total.add(amount);
            }

            return total.doubleValue();
        } catch (SQLException e) {
            System.err.println("Error getting balance: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public Map<Integer, Transaction> getAllTransactions() {
        throw new IllegalArgumentException("Not implemented");
        // select id, name, amount, timestamp from transactions;
    }

    @Override
    public Map<Integer, Transaction> getTransactionsBefore(long timestamp) {
        throw new IllegalArgumentException("Not implemented");
        // select id, name, amount, timestamp from transactions where this.timestamp < timestamp;
    }

    @Override
    public Map<Integer, Transaction> getTransactionsBetween(long from, long until) {
        throw new IllegalArgumentException("Not implemented");
        // select id, name, amount, timestamp from transactions where this.timestamp > from and this.timestamp < until;
    }

    @Override
    public Map<Integer, Transaction> find(String toSearch) {
        throw new IllegalArgumentException("Not implemented");
    }
}
