package com.passManager.database;

import java.sql.*;

public class Database {

    public static final String DB_URL = "jdbc:sqlite:passwordDatabase.db";

    public Database (){
        try {
            Class.forName("org.sqlite.JDBC").getConstructor().newInstance();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS passwords (id INTEGER PRIMARY KEY AUTOINCREMENT, site TEXT NOT NULL, title TEXT NOT NULL, strong_password INTEGER DEFAULT 0, password TEXT)")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }

    }

    public void saveEntity(String site, String title, Integer strong, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO passwords (site, title, strong_password, password) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, site);
            stmt.setString(2, title);
            stmt.setInt(3, strong);
            stmt.setString(4, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding password: " + e.getMessage());
        }
    }

    public String getPasswordByField(String field, String value) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = createPreparedStatementForField(conn, field, value)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
            System.out.println("Password not found");
        } catch (SQLException e) {
            System.out.println("Error getting password: " + e.getMessage());
        }
        return null;
    }

    public PreparedStatement createPreparedStatementForField(Connection conn, String field, String value) throws SQLException {
        String query;
        if ("id".equals(field)) {
            // Check if value is a valid integer
            try {
                int idValue = Integer.parseInt(value);
                query = "SELECT password FROM passwords WHERE " + field + " = ?";
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid value for 'id': " + value + " is not a valid integer.");
            }
        } else {
            query = "SELECT password FROM passwords WHERE " + field + " = ?";
        }

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, value);
        return stmt;
    }

// In case it is decided the method to return null instead of exception
//    private PreparedStatement createPreparedStatementForField(Connection conn, String field, String value) throws SQLException {
//        String query = "SELECT password FROM passwords WHERE " + field + " = ?";
//        PreparedStatement stmt = conn.prepareStatement(query);
//        stmt.setString(1, value);
//        return stmt;
//    }

    public void deletePasswordByField(String field, String value) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = createPreparedStatementForDeletingField(conn, field, value)) {
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password record deleted");
            } else {
                System.out.println("Password record not found");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting password record: " + e.getMessage());
        }
    }

    public PreparedStatement createPreparedStatementForDeletingField(Connection conn, String field, String value) throws SQLException {
        String query;
        if ("id".equals(field)) {
            // Check if value is a valid integer
            try {
                int idValue = Integer.parseInt(value);
                query = "DELETE FROM passwords WHERE " + field + " = ?";
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid value for 'id': " + value + " is not a valid integer.");
            }
        } else {
            query = "DELETE FROM passwords WHERE " + field + " = ?";
        }

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, value);
        return stmt;
    }



}
