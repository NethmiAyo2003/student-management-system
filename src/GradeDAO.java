// src/GradeDAO.java

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GradeDAO {
    
    // Calculate average grade for a course
    public double getCourseAverage(int courseId) {
        double average = 0.0;
        String sql = "SELECT AVG((marks / max_marks) * 100) as average " +
                     "FROM grades WHERE course_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                average = rs.getDouble("average");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return average;
    }
    
    // Get student average for a specific course
    public double getStudentCourseAverage(int studentId, int courseId) {
        double average = 0.0;
        String sql = "SELECT AVG((marks / max_marks) * 100) as average " +
                     "FROM grades WHERE student_id = ? AND course_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                average = rs.getDouble("average");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return average;
    }
    
    // Get all students with their averages for a course
    public Map<Integer, Double> getStudentAveragesByCourse(int courseId) {
        Map<Integer, Double> studentAverages = new HashMap<>();
        String sql = "SELECT student_id, AVG((marks / max_marks) * 100) as average " +
                     "FROM grades WHERE course_id = ? GROUP BY student_id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                double average = rs.getDouble("average");
                studentAverages.put(studentId, average);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return studentAverages;
    }
    
    // Get grade statistics for a course
    public Map<String, Double> getCourseStatistics(int courseId) {
        Map<String, Double> stats = new HashMap<>();
        String sql = "SELECT " +
                     "AVG((marks / max_marks) * 100) as average, " +
                     "MAX((marks / max_marks) * 100) as highest, " +
                     "MIN((marks / max_marks) * 100) as lowest, " +
                     "COUNT(DISTINCT student_id) as total_students " +
                     "FROM grades WHERE course_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                stats.put("average", rs.getDouble("average"));
                stats.put("highest", rs.getDouble("highest"));
                stats.put("lowest", rs.getDouble("lowest"));
                stats.put("total_students", rs.getDouble("total_students"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return stats;
    }
}