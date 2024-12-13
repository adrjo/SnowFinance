package com.github.adrjo.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    public static void applySchema(Connection conn) {
        //create tables from schema in resources/schema.sql
    }

    public static boolean databaseExists(Connection connection, String database) {
        try (Statement stmt = connection.createStatement()) {
            String query = "SELECT 1 FROM pg_database WHERE datname = '" + database + "'";
            if (stmt.execute(query)) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return false;
    }
}
