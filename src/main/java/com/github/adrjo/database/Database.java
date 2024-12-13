package com.github.adrjo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static final String DATABASE = "SnowFinance";

    private Connection connection;

    public Database() {
        connect();
    }

    private void connect() {
        // initial connection
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // try create snowfinance db
            try (Statement statement = connection.createStatement()) {
                String createDbQuery = "CREATE DATABASE " + DATABASE;
                statement.executeUpdate(createDbQuery);
                System.out.println("Database '" + DATABASE + "' created successfully");
            }

            // connect to created db
            String newDbUrl = URL + DATABASE;
            try (Connection newDbConnection = DriverManager.getConnection(newDbUrl, USER, PASSWORD)) {
                this.connection = newDbConnection;
                System.out.println("Connected to " + DATABASE);

            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
