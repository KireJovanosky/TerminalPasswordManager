package com.passManager.database;

import java.sql.*;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:passwordDatabase.db";

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

    public String getPasswordBySite(String site) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT password FROM passwords WHERE site = ?")) {
            stmt.setString(1, site);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            System.out.println("Error getting password: " + e.getMessage());
        }
        return null;
    }

    public String getPasswordByTitle(String title) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT password FROM passwords WHERE title = ?")) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");

            }
        } catch (SQLException e) {
            System.out.println("Error getting password: " + e.getMessage());
        }
        return null;
    }

    public String getPasswordById(Integer id) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT password FROM passwords WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            System.out.println("Error getting password: " + e.getMessage());
        }
        return null;
    }

}
