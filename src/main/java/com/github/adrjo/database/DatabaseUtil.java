package com.github.adrjo.database;

import com.github.adrjo.util.Helper;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    public static void applySchema(Connection conn) {
        //create tables from schema in resources/schema.sql

        URL url = Thread.currentThread().getContextClassLoader().getResource("schema.sql");
        if (url == null) return;
        try {
            Path schema = Helper.getResourceAsPath(url, "schema.sql");
            String[] queries = Files.readString(schema).split(";");

            try (Statement stmt = conn.createStatement()) {
                for (String query : queries) {
                    stmt.addBatch(query);
                }
                stmt.executeBatch();
            }

        } catch (Exception e) {
            System.err.println("Error: couldn't read schema: " + e.getMessage());
        }
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
