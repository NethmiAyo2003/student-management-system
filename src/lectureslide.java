// src/LectureSlide.java

import java.sql.Timestamp;

public class lectureslide {
    private int slideId;
    private int courseId;
    private String title;
    private String filePath;
    private Timestamp uploadDate;
    private String description;
    
    // Constructor
    public lectureslide() {}
    
    public lectureslide(int slideId, String title, String filePath, String description) {
        this.slideId = slideId;
        this.title = title;
        this.filePath = filePath;
        this.description = description;
    }
    
    // Getters and Setters
    public int getSlideId() {
        return slideId;
    }
    
    public void setSlideId(int slideId) {
        this.slideId = slideId;
    }
    
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public Timestamp getUploadDate() {
        return uploadDate;
    }
    
    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}