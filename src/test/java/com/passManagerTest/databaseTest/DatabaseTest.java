package com.passManagerTest.databaseTest;

import com.passManager.database.Database;
import org.testng.annotations.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DatabaseTest {

    private static Database database;

    @BeforeClass
    public static void setUpClass() {
        database = new Database();
    }

    @Before
    public void setUp() {
        // Clear the database table before each test
        try (Connection conn = DriverManager.getConnection(Database.DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM passwords");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDownClass() {
        // Close the database connection after all tests are done
        try {
            DriverManager.getConnection(Database.DB_URL).close();
            // Optionally, you can delete the database file after all tests are done
            File dbFile = new File("passwordDatabase.db");
            dbFile.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateTable() {
        // Test if the table is created successfully
        database.createTable();

        // Check if the table exists in the database
        try (Connection conn = DriverManager.getConnection(Database.DB_URL);
             Statement stmt = conn.createStatement()) {
            boolean tableExists = stmt.execute("SELECT * FROM passwords");
            assertEquals(true, tableExists);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveEntityAndGetPasswordByField() {
        // Test saving an entity and retrieving its password
        database.createTable();
        database.saveEntity("example.com", "Example", 1, "securePassword");

        String password = database.getPasswordByField("site", "example.com");
        assertEquals("securePassword", password);

        // Test retrieving a password that does not exist
        String nonExistentPassword = database.getPasswordByField("site", "nonexistent.com");
        assertNull(nonExistentPassword);
    }
}

