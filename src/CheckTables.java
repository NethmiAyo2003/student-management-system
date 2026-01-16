import java.sql.*;

public class CheckTables {
    public static void main(String[] args) {
        try {
            Connection conn = DBConfig.getConnection();
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables("nethmi", null, "%", new String[]{"TABLE"});

            System.out.println("Existing tables in database:");
            System.out.println("============================");
            while (rs.next()) {
                System.out.println("- " + rs.getString("TABLE_NAME"));
            }
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
