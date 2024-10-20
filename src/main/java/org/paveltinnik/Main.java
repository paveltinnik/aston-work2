package org.paveltinnik;

import org.paveltinnik.config.DatabaseConfig;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        //TODO

//        try (Connection connection = DatabaseConfig.getConnection()) {
//            executeSqlScript(connection, "schema.sql");
//            System.out.println("Database initialized and test data inserted successfully.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.err.println("Failed to initialize the database.");
//        }
    }

//    private static void executeSqlScript(Connection connection, String scriptFile) throws SQLException {
//        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(scriptFile);
//             Statement statement = connection.createStatement()) {
//            if (inputStream == null) {
//                throw new RuntimeException("SQL script file not found: " + scriptFile);
//            }
//            String script = new String(inputStream.readAllBytes());
//            statement.executeUpdate(script);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to execute SQL script: " + scriptFile, e);
//        }
//    }
}