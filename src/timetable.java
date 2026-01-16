// src/Timetable.java

import java.sql.Time;

public class timetable {
    private int timetableId;
    private int courseId;
    private String courseCode;
    private String courseName;
    private String dayOfWeek;
    private Time startTime;
    private Time endTime;
    private String roomNumber;
    
    // Constructor
    public timetable() {}
    
    public timetable(String dayOfWeek, Time startTime, Time endTime, 
                     String courseCode, String courseName, String roomNumber) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.roomNumber = roomNumber;
    }
    
    // Getters and Setters
    public int getTimetableId() {
        return timetableId;
    }
    
    public void setTimetableId(int timetableId) {
        this.timetableId = timetableId;
    }
    
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public Time getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    
    public Time getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}