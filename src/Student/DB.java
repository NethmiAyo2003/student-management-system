package sms.db;

import java.sql.*;

public class DB {

    // ================= ENROLLED COURSES =================
    public static String getEnrolledCourses(int studentId) {
        StringBuilder sb = new StringBuilder();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                "select c.course_name, c.course_code, c.credits " +
                "from enrollments e " +
                "join courses c on e.course_id = c.course_id " +
                "where e.student_id = ?"
            );
            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                sb.append("• ")
                  .append(rs.getString("course_code"))
                  .append(" - ")
                  .append(rs.getString("course_name"))
                  .append(" (")
                  .append(rs.getInt("credits"))
                  .append(" credits)\n");
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
                "select c.course_name, a.percentage " +
                "from attendance a " +
                "join courses c on a.course_id = c.course_id " +
                "where a.student_id = ?"
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
                "select c.course_name, g.assignment_name, g.marks, g.max_marks " +
                "from grades g " +
                "join courses c on g.course_id = c.course_id " +
                "where g.student_id = ?"
            );
            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                sb.append(rs.getString("course_name"))
                  .append(" - ")
                  .append(rs.getString("assignment_name"))
                  .append(": ")
                  .append(rs.getDouble("marks"))
                  .append("/")
                  .append(rs.getDouble("max_marks"))
                  .append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading grades.";
        }

        return sb.length() == 0 ? "No grades found." : sb.toString();
    }
}
