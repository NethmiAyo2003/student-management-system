// src/StudentAveragePanel.java

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class StudentAveragePanel extends JPanel {
    
    private int lecturerId;
    private JComboBox<Course> courseComboBox;
    private JTable averageTable;
    private DefaultTableModel tableModel;
    private JLabel lblCourseAverage;
    private JLabel lblHighest;
    private JLabel lblLowest;
    private JLabel lblTotalStudents;
    private JButton btnRefresh;
    
    private CourseDAO courseDAO;
    private GradeDAO gradeDAO;
    private studentDAO studentDAO;
    
    public StudentAveragePanel(int lecturerId) {
        this.lecturerId = lecturerId;
        this.courseDAO = new CourseDAO();
        this.gradeDAO = new GradeDAO();
        this.studentDAO = new studentDAO();
        
        initComponents();
        loadCourses();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("📊 Student Average & Statistics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(41, 128, 185));
        titlePanel.add(titleLabel);
        
        // Control Panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBackground(Color.WHITE);
        
        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        courseComboBox = new JComboBox<>();
        courseComboBox.setPreferredSize(new Dimension(300, 30));
        courseComboBox.addActionListener(e -> loadAverages());
        
        btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(e -> loadAverages());
        
        controlPanel.add(courseLabel);
        controlPanel.add(courseComboBox);
        controlPanel.add(btnRefresh);
        
        // Statistics Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 10));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Course Average Card
        JPanel avgCard = createStatCard("Course Average", "0.00%", new Color(52, 152, 219));
        lblCourseAverage = (JLabel) avgCard.getComponent(1);
        
        // Highest Grade Card
        JPanel highCard = createStatCard("Highest", "0.00%", new Color(46, 204, 113));
        lblHighest = (JLabel) highCard.getComponent(1);
        
        // Lowest Grade Card
        JPanel lowCard = createStatCard("Lowest", "0.00%", new Color(231, 76, 60));
        lblLowest = (JLabel) lowCard.getComponent(1);
        
        // Total Students Card
        JPanel totalCard = createStatCard("Total Students", "0", new Color(155, 89, 182));
        lblTotalStudents = (JLabel) totalCard.getComponent(1);
        
        statsPanel.add(avgCard);
        statsPanel.add(highCard);
        statsPanel.add(lowCard);
        statsPanel.add(totalCard);
        
        // Table
        String[] columns = {"Student ID", "Registration No.", "Student Name", "Average (%)", "Grade"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        averageTable = new JTable(tableModel);
        averageTable.setRowHeight(30);
        averageTable.setFont(new Font("Arial", Font.PLAIN, 12));
        averageTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        averageTable.getTableHeader().setBackground(new Color(52, 152, 219));
        averageTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(averageTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        
        // Assemble panels
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(controlPanel, BorderLayout.CENTER);
        topPanel.add(statsPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.BOLD, 28));
        lblValue.setForeground(Color.WHITE);
        lblValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(lblTitle);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(lblValue);
        
        return card;
    }
    
    private void loadCourses() {
        courseComboBox.removeAllItems();
        List<Course> courses = courseDAO.getCoursesByLecturer(lecturerId);
        for (Course course : courses) {
            courseComboBox.addItem(course);
        }
        
        if (courseComboBox.getItemCount() > 0) {
            loadAverages();
        }
    }
    
    private void loadAverages() {
        tableModel.setRowCount(0);
        
        Course selectedCourse = (Course) courseComboBox.getSelectedItem();
        if (selectedCourse == null) {
            return;
        }
        
        int courseId = selectedCourse.getCourseId();
        
        // Load statistics
        Map<String, Double> stats = gradeDAO.getCourseStatistics(courseId);
        lblCourseAverage.setText(String.format("%.2f%%", stats.getOrDefault("average", 0.0)));
        lblHighest.setText(String.format("%.2f%%", stats.getOrDefault("highest", 0.0)));
        lblLowest.setText(String.format("%.2f%%", stats.getOrDefault("lowest", 0.0)));
        lblTotalStudents.setText(String.valueOf(stats.getOrDefault("total_students", 0.0).intValue()));
        
        // Load student averages
        Map<Integer, Double> studentAverages = gradeDAO.getStudentAveragesByCourse(courseId);
        List<Student> students = studentDAO.getStudentsByCourse(courseId);
        
        for (Student student : students) {
            int studentId = student.getStudentId();
            double average = studentAverages.getOrDefault(studentId, 0.0);
            String grade = calculateGrade(average);
            
            Object[] row = {
                studentId,
                student.getRegistrationNumber(),
                student.getFullName(),
                String.format("%.2f", average),
                grade
            };
            tableModel.addRow(row);
        }
    }
    
    private String calculateGrade(double average) {
        if (average >= 85) return "A+";
        else if (average >= 80) return "A";
        else if (average >= 75) return "A-";
        else if (average >= 70) return "B+";
        else if (average >= 65) return "B";
        else if (average >= 60) return "B-";
        else if (average >= 55) return "C+";
        else if (average >= 50) return "C";
        else if (average >= 45) return "C-";
        else if (average >= 40) return "D";
        else return "F";
    }
}