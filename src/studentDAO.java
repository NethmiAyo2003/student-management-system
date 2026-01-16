// src/StudentDAO.java

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class studentDAO {
    
    // Search students by name or registration number
    public List<Student> searchStudents(String searchTerm) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE " +
                     "first_name LIKE ? OR last_name LIKE ? OR registration_number LIKE ? " +
                     "ORDER BY first_name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setUserId(rs.getInt("user_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setRegistrationNumber(rs.getString("registration_number"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setDepartment(rs.getString("department"));
                student.setYearOfStudy(rs.getInt("year_of_study"));
                students.add(student);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return students;
    }
    
    // Get all students enrolled in a specific course
    public List<Student> getStudentsByCourse(int courseId) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.* FROM students s " +
                     "JOIN enrollments e ON s.student_id = e.student_id " +
                     "WHERE e.course_id = ? " +
                     "ORDER BY s.first_name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setRegistrationNumber(rs.getString("registration_number"));
                student.setEmail(rs.getString("email"));
                student.setDepartment(rs.getString("department"));
                students.add(student);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return students;
    }
    
    // Get student by ID
    public Student getStudentById(int studentId) {
        Student student = null;
        String sql = "SELECT * FROM students WHERE student_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setRegistrationNumber(rs.getString("registration_number"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setDepartment(rs.getString("department"));
                student.setYearOfStudy(rs.getInt("year_of_study"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return student;
    }
    
    // Get all students
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY first_name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setRegistrationNumber(rs.getString("registration_number"));
                student.setEmail(rs.getString("email"));
                student.setDepartment(rs.getString("department"));
                students.add(student);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return students;
    }
}