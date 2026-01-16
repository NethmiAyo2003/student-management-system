

import java.sql.*;

import sms.db.DBConnection;

public class DB {

    public static String getEnrolledCourses(int studentId) {
    StringBuilder sb = new StringBuilder();

    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(
            "SELECT c.course_name " +
            "FROM enrollments e " +
            "JOIN courses c ON e.course_id = c.id " +
            "WHERE e.student_id = ?"
        );
        pst.setInt(1, studentId);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            sb.append("- ")
              .append(rs.getString("course_name"))
              .append("\n");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return sb.length() == 0 ? "No courses found." : sb.toString();
}


    public static String getAttendance(int studentId) {
    StringBuilder sb = new StringBuilder();

    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(
            "SELECT c.course_name, a.percentage " +
            "FROM attendance a " +
            "JOIN courses c ON a.course_id = c.id " +
            "WHERE a.student_id = ?"
        );
        pst.setInt(1, studentId);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            sb.append(rs.getString("course_name"))
              .append(": ")
              .append(rs.getInt("percentage"))
              .append("%\n");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return sb.length() == 0 ? "No attendance records." : sb.toString();
}

    public static String getGrades(int studentId) {
    StringBuilder sb = new StringBuilder();

    try {
        Connection con = DBConnection.getConnection();
        PreparedStatement pst = con.prepareStatement(
            "SELECT c.course_name, g.grade " +
            "FROM grades g " +
            "JOIN courses c ON g.course_id = c.id " +
            "WHERE g.student_id = ?"
        );
        pst.setInt(1, studentId);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            sb.append(rs.getString("course_name"))
              .append(": ")
              .append(rs.getString("grade"))
              .append("\n");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return sb.length() == 0 ? "No grades found." : sb.toString();
    }


    public static Connection getConnection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getConnection'");
    }
}






