package sms.db;

import java.sql.*;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://161.97.122.70:3306/nethmi";
    private static final String DB_USER = "nethmi";
    private static final String DB_PASSWORD = "Simple!o@in";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
