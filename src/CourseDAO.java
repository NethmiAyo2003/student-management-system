// src/CourseDAO.java

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    
    // Get all courses taught by a specific lecturer
    public List<Course> getCoursesByLecturer(int lecturerId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE lecturer_id = ? ORDER BY course_code";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, lecturerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                course.setLecturerId(rs.getInt("lecturer_id"));
                course.setCredits(rs.getInt("credits"));
                course.setSemester(rs.getString("semester"));
                courses.add(course);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return courses;
    }
    
    // Get course by ID
    public Course getCourseById(int courseId) {
        Course course = null;
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                course.setLecturerId(rs.getInt("lecturer_id"));
                course.setCredits(rs.getInt("credits"));
                course.setSemester(rs.getString("semester"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return course;
    }
    
    // Get all courses
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY course_code";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseCode(rs.getString("course_code"));
                course.setCourseName(rs.getString("course_name"));
                course.setCredits(rs.getInt("credits"));
                courses.add(course);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return courses;
    }
}