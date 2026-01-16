// src/LectureSlidesPanel.java

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LectureSlidePanel extends JPanel {
    
    private int lecturerId;
    private JComboBox<Course> courseComboBox;
    private JTable slidesTable;
    private DefaultTableModel tableModel;
    private JButton btnAddSlide;
    private JButton btnDeleteSlide;
    private JButton btnRefresh;
    
    private CourseDAO courseDAO;
    private lectureslideDAO slideDAO;
    
    public LectureSlidePanel(int lecturerId) {
        this.lecturerId = lecturerId;
        this.courseDAO = new CourseDAO();
        this.slideDAO = new lectureslideDAO();
        
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
        JLabel titleLabel = new JLabel("📚 Lecture Slides Management");
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
        courseComboBox.addActionListener(e -> loadSlides());
        
        btnAddSlide = new JButton("+ Add Slide");
        btnAddSlide.setBackground(new Color(46, 204, 113));
        btnAddSlide.setForeground(Color.WHITE);
        btnAddSlide.setFocusPainted(false);
        btnAddSlide.addActionListener(e -> addSlide());
        
        btnDeleteSlide = new JButton("🗑 Delete");
        btnDeleteSlide.setBackground(new Color(231, 76, 60));
        btnDeleteSlide.setForeground(Color.WHITE);
        btnDeleteSlide.setFocusPainted(false);
        btnDeleteSlide.addActionListener(e -> deleteSlide());
        
        btnRefresh = new JButton("🔄 Refresh");
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(e -> loadSlides());
        
        controlPanel.add(courseLabel);
        controlPanel.add(courseComboBox);
        controlPanel.add(btnAddSlide);
        controlPanel.add(btnDeleteSlide);
        controlPanel.add(btnRefresh);
        
        // Table
        String[] columns = {"Slide ID", "Title", "File Path", "Description", "Upload Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        slidesTable = new JTable(tableModel);
        slidesTable.setRowHeight(30);
        slidesTable.setFont(new Font("Arial", Font.PLAIN, 12));
        slidesTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        slidesTable.getTableHeader().setBackground(new Color(52, 152, 219));
        slidesTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(slidesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        
        // Add components
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(controlPanel, BorderLayout.NORTH);
        add(topPanel, BorderLayout.NORTH);
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadCourses() {
        courseComboBox.removeAllItems();
        List<Course> courses = courseDAO.getCoursesByLecturer(lecturerId);
        for (Course course : courses) {
            courseComboBox.addItem(course);
        }
        
        if (courseComboBox.getItemCount() > 0) {
            loadSlides();
        }
    }
    
    private void loadSlides() {
        tableModel.setRowCount(0);
        
        Course selectedCourse = (Course) courseComboBox.getSelectedItem();
        if (selectedCourse != null) {
            List<lectureslide> slides = slideDAO.getSlidesByCourse(selectedCourse.getCourseId());
            
            for (lectureslide slide : slides) {
                Object[] row = {
                    slide.getSlideId(),
                    slide.getTitle(),
                    slide.getFilePath(),
                    slide.getDescription(),
                    slide.getUploadDate()
                };
                tableModel.addRow(row);
            }
        }
    }
    
    private void addSlide() {
        Course selectedCourse = (Course) courseComboBox.getSelectedItem();
        if (selectedCourse == null) {
            JOptionPane.showMessageDialog(this, "Please select a course first!");
            return;
        }
        
        // Create dialog for adding slide
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add Lecture Slide", true);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));
        dialog.setSize(500, 250);
        dialog.setLocationRelativeTo(this);
        
        JLabel lblTitle = new JLabel("Title:");
        JTextField txtTitle = new JTextField();
        
        JLabel lblFilePath = new JLabel("File Path:");
        JTextField txtFilePath = new JTextField();
        
        JLabel lblDescription = new JLabel("Description:");
        JTextArea txtDescription = new JTextArea(3, 20);
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        
        JButton btnSave = new JButton("Save");
        JButton btnCancel = new JButton("Cancel");
        
        btnSave.addActionListener(e -> {
            String title = txtTitle.getText().trim();
            String filePath = txtFilePath.getText().trim();
            String description = txtDescription.getText().trim();
            
            if (title.isEmpty() || filePath.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Title and File Path are required!");
                return;
            }
            
            lectureslide slide = new lectureslide();
            slide.setCourseId(selectedCourse.getCourseId());
            slide.setTitle(title);
            slide.setFilePath(filePath);
            slide.setDescription(description);
            
            if (slideDAO.addSlide(slide)) {
                JOptionPane.showMessageDialog(dialog, "Slide added successfully!");
                loadSlides();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to add slide!");
            }
        });
        
        btnCancel.addActionListener(e -> dialog.dispose());
        
        dialog.add(lblTitle);
        dialog.add(txtTitle);
        dialog.add(lblFilePath);
        dialog.add(txtFilePath);
        dialog.add(lblDescription);
        dialog.add(scrollDesc);
        dialog.add(btnSave);
        dialog.add(btnCancel);
        
        dialog.setVisible(true);
    }
    
    private void deleteSlide() {
        int selectedRow = slidesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a slide to delete!");
            return;
        }
        
        int slideId = (int) tableModel.getValueAt(selectedRow, 0);
        String title = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete: " + title + "?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (slideDAO.deleteSlide(slideId)) {
                JOptionPane.showMessageDialog(this, "Slide deleted successfully!");
                loadSlides();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete slide!");
            }
        }
    }
}