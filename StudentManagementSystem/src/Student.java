public class Student {

    private int id;
    private String username;
    private String studentId;
    private String name;
    private int age;
    private String course;
    private double gpa;
    private int credits;
    private String photo;

    public Student(int id, String username, String studentId, String name,
                   int age, String course, double gpa, int credits, String photo) {
        this.id = id;
        this.username = username;
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.course = course;
        this.gpa = gpa;
        this.credits = credits;
        this.photo = photo;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCourse() { return course; }
    public double getGpa() { return gpa; }
    public int getCredits() { return credits; }
    public String getPhoto() { return photo; }
}
