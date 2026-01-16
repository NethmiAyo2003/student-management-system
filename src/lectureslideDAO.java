// src/LectureSlideDAO.java

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class lectureslideDAO {
    
    // Get all slides for a specific course
    public List<lectureslide> getSlidesByCourse(int courseId) {
        List<lectureslide> slides = new ArrayList<>();
        String sql = "SELECT * FROM lecture_slides WHERE course_id = ? ORDER BY upload_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                lectureslide slide = new lectureslide();
                slide.setSlideId(rs.getInt("slide_id"));
                slide.setCourseId(rs.getInt("course_id"));
                slide.setTitle(rs.getString("title"));
                slide.setFilePath(rs.getString("file_path"));
                slide.setUploadDate(rs.getTimestamp("upload_date"));
                slide.setDescription(rs.getString("description"));
                slides.add(slide);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return slides;
    }
    
    // Add new lecture slide
    public boolean addSlide(lectureslide slide) {
        String sql = "INSERT INTO lecture_slides (course_id, title, file_path, description) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, slide.getCourseId());
            pstmt.setString(2, slide.getTitle());
            pstmt.setString(3, slide.getFilePath());
            pstmt.setString(4, slide.getDescription());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Delete slide
    public boolean deleteSlide(int slideId) {
        String sql = "DELETE FROM lecture_slides WHERE slide_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, slideId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}