import java.sql.*;

public class DBUtil {

    // Delegates to DBConfig for centralized configuration
    public static Connection getConnection() throws Exception {
        Connection conn = DBConfig.getConnection();
        if (conn == null) {
            throw new SQLException("Failed to establish database connection");
        }
        return conn;
    }
}
