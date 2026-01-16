package sms.db;

import java.sql.*;

public class DB {

    // ================= ENROLLED COURSES =================
    public static String getEnrolledCourses(int studentId) {
        StringBuilder sb = new StringBuilder();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                "SELECT course_name FROM enrollments WHERE student_id = ?"
            );
            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                sb.append("• ")
                  .append(rs.getString("course_name"))
                  .append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading courses.";
        }

        return sb.length() == 0 ? "No enrolled courses found." : sb.toString();
    }

    // ================= ATTENDANCE =================
    public static String getAttendance(int studentId) {
        StringBuilder sb = new StringBuilder();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                "SELECT course_name, percentage FROM attendance WHERE student_id = ?"
            );
            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                sb.append(rs.getString("course_name"))
                  .append(" : ")
                  .append(rs.getInt("percentage"))
                  .append("%\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading attendance.";
        }

        return sb.length() == 0 ? "No attendance records found." : sb.toString();
    }

    // ================= GRADES =================
    public static String getGrades(int studentId) {
        StringBuilder sb = new StringBuilder();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                "SELECT course_name, grade FROM grades WHERE student_id = ?"
            );
            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                sb.append(rs.getString("course_name"))
                  .append(" : ")
                  .append(rs.getString("grade"))
                  .append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading grades.";
        }

        return sb.length() == 0 ? "No grades found." : sb.toString();
    }
}
