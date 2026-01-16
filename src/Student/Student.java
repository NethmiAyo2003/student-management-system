package sms.model;

public class Student {

    private int id;
    private int enrollmentYear;

    private String name;
    private String email;
    private String phone;
    private String degree;
    private String faculty;
    private String gender;
    private String dob;
    private String address;

    // -------- GETTERS --------
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDegree() { return degree; }
    public String getFaculty() { return faculty; }
    public int getEnrollmentYear() { return enrollmentYear; }
    public String getGender() { return gender; }
    public String getDob() { return dob; }
    public String getAddress() { return address; }

    // -------- SETTERS (VERY IMPORTANT) --------
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDegree(String degree) { this.degree = degree; }
    public void setFaculty(String faculty) { this.faculty = faculty; }
    public void setEnrollmentYear(int enrollmentYear) { this.enrollmentYear = enrollmentYear; }
    public void setGender(String gender) { this.gender = gender; }
    public void setDob(String dob) { this.dob = dob; }
    public void setAddress(String address) { this.address = address; }
}
