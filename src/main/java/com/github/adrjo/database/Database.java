package com.github.adrjo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static final String DATABASE = "snowfinance";

    private Connection connection;

    public Database() {
        connect(URL);

        if (!DatabaseUtil.databaseExists(connection, DATABASE)) {
            createDatabase();
        }
        connect(URL + DATABASE);
    }

    private void createDatabase() {
        // try create snowfinance db
        try (Statement statement = connection.createStatement()) {
            String createDbQuery = "CREATE DATABASE " + DATABASE;
            statement.executeUpdate(createDbQuery);
            System.out.println("Database '" + DATABASE + "' created successfully");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void connect(String url) {
        try {
            this.connection = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("Connected to " + url);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
