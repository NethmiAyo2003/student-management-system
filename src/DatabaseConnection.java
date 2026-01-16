// src/DatabaseConnection.java
// This class now delegates to DBConfig for centralized configuration

import java.sql.Connection;

public class DatabaseConnection {

    // Private constructor to prevent instantiation
    private DatabaseConnection() {}

    // Get database connection - delegates to DBConfig
    public static Connection getConnection() {
        return DBConfig.getConnection();
    }

    // Close connection - delegates to DBConfig
    public static void closeConnection() {
        DBConfig.closeConnection();
    }

    // Test connection
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Connection test successful!");
            closeConnection();
        } else {
            System.out.println("❌ Connection test failed!");
        }
    }
}