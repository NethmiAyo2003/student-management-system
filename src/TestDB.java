
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDB {

    public static void main(String[] args) {

        // MySQL database URL, username, password
        String url = "jdbc:mysql://localhost:3306/testdb"; // database name = testdb
        String user = "root";        // MySQL username
        String password = "Lavandi2004@";    // MySQL password

        try {
            // Connect to MySQL
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("MySQL Connected Successfully!");

            // Close connection
            con.close();
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
    }
}
