// src/TimetableDAO.java

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class timetableDAO {
    
    // Get timetable for a specific lecturer
    public List<timetable> getTimetableByLecturer(int lecturerId) {
        List<timetable> timetable = new ArrayList<>();
        String sql = "SELECT t.*, c.course_code, c.course_name " +
                     "FROM timetable t " +
                     "JOIN courses c ON t.course_id = c.course_id " +
                     "WHERE c.lecturer_id = ? " +
                     "ORDER BY FIELD(t.day_of_week, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'), t.start_time";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, lecturerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                timetable entry = new timetable();
                entry.setTimetableId(rs.getInt("timetable_id"));
                entry.setCourseId(rs.getInt("course_id"));
                entry.setCourseCode(rs.getString("course_code"));
                entry.setCourseName(rs.getString("course_name"));
                entry.setDayOfWeek(rs.getString("day_of_week"));
                entry.setStartTime(rs.getTime("start_time"));
                entry.setEndTime(rs.getTime("end_time"));
                entry.setRoomNumber(rs.getString("room_number"));
                timetable.add(entry);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return timetable;
    }
    
    // Get timetable for a specific course
    public List<timetable> getTimetableByCourse(int courseId) {
        List<timetable> timetable = new ArrayList<>();
        String sql = "SELECT t.*, c.course_code, c.course_name " +
                     "FROM timetable t " +
                     "JOIN courses c ON t.course_id = c.course_id " +
                     "WHERE t.course_id = ? " +
                     "ORDER BY FIELD(t.day_of_week, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'), t.start_time";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                timetable entry = new timetable();
                entry.setTimetableId(rs.getInt("timetable_id"));
                entry.setCourseId(rs.getInt("course_id"));
                entry.setCourseCode(rs.getString("course_code"));
                entry.setCourseName(rs.getString("course_name"));
                entry.setDayOfWeek(rs.getString("day_of_week"));
                entry.setStartTime(rs.getTime("start_time"));
                entry.setEndTime(rs.getTime("end_time"));
                entry.setRoomNumber(rs.getString("room_number"));
                timetable.add(entry);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return timetable;
    }
}