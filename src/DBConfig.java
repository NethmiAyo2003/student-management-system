import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centralized Database Configuration
 * Update these credentials to connect to your VPS MySQL database
 */
public class DBConfig {

    // ========== DATABASE CREDENTIALS - UPDATE THESE FOR YOUR VPS ==========

    // Database URL - Format: jdbc:mysql://YOUR_VPS_IP:PORT/DATABASE_NAME
    // Example: jdbc:mysql://192.168.1.100:3306/student_management_system
    private static final String DB_URL = "jdbc:mysql://161.97.122.70:3306/nethmi";

    // Database Username
    private static final String DB_USER = "nethmi";

    // Database Password
    private static final String DB_PASSWORD = "Simple!o@in";

    // ======================================================================

    private static Connection connection = null;

    // Private constructor to prevent instantiation
    private DBConfig() {}

    /**
     * Get database connection (Singleton pattern)
     * @return Connection object or null if connection fails
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load MySQL JDBC Driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Create connection
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("✓ Database connected successfully!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("✗ MySQL JDBC Driver not found!");
            System.err.println("  Make sure mysql-connector-j-9.5.0.jar is in the lib folder");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("✗ Database connection failed!");
            System.err.println("  Please check your database credentials in DBConfig.java");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error closing connection!");
            e.printStackTrace();
        }
    }

    /**
     * Test database connection
     */
    public static void main(String[] args) {
        System.out.println("Testing Database Connection...");
        System.out.println("================================");
        Connection conn = DBConfig.getConnection();
        if (conn != null) {
            System.out.println("✓ Connection test successful!");
            closeConnection();
        } else {
            System.out.println("✗ Connection test failed!");
            System.out.println("\nPlease update the following in DBConfig.java:");
            System.out.println("  - DB_URL: Your VPS MySQL server address");
            System.out.println("  - DB_USER: Your MySQL username");
            System.out.println("  - DB_PASSWORD: Your MySQL password");
        }
    }
}
