// src/StudentSearchPanel.java

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentSearchPanel extends JPanel {
    
    private JTextField txtSearch;
    private JButton btnSearch;
    private JButton btnClear;
    private JButton btnViewDetails;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JLabel lblResultCount;
    
    private studentDAO studentDAO;
    
    public StudentSearchPanel() {
        this.studentDAO = new studentDAO();
        
        initComponents();
        loadAllStudents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("🔍 Smart Student Search");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(41, 128, 185));
        titlePanel.add(titleLabel);
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            "Search by Name or Registration Number",
            0,
            0,
            new Font("Arial", Font.PLAIN, 12),
            new Color(127, 140, 141)
        ));
        
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        txtSearch = new JTextField(30);
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(300, 35));
        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchStudents();
                }
            }
        });
        
        btnSearch = new JButton("🔍 Search");
        btnSearch.setBackground(new Color(52, 152, 219));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setPreferredSize(new Dimension(120, 35));
        btnSearch.addActionListener(e -> searchStudents());
        
        btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(149, 165, 166));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        btnClear.setPreferredSize(new Dimension(100, 35));
        btnClear.addActionListener(e -> clearSearch());
        
        searchPanel.add(searchLabel);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnClear);
        
        // Result Info Panel
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        resultPanel.setBackground(Color.WHITE);
        
        lblResultCount = new JLabel("Total students: 0");
        lblResultCount.setFont(new Font("Arial", Font.BOLD, 13));
        lblResultCount.setForeground(new Color(52, 73, 94));
        
        btnViewDetails = new JButton("📋 View Details");
        btnViewDetails.setBackground(new Color(46, 204, 113));
        btnViewDetails.setForeground(Color.WHITE);
        btnViewDetails.setFocusPainted(false);
        btnViewDetails.addActionListener(e -> viewStudentDetails());
        
        resultPanel.add(lblResultCount);
        resultPanel.add(Box.createHorizontalStrut(20));
        resultPanel.add(btnViewDetails);
        
        // Table
        String[] columns = {"Student ID", "Registration No.", "First Name", "Last Name", "Email", "Department", "Year"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(30);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        studentTable.getTableHeader().setBackground(new Color(52, 152, 219));
        studentTable.getTableHeader().setForeground(Color.WHITE);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Set column widths
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        studentTable.getColumnModel().getColumn(4).setPreferredWidth(200);
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        studentTable.getColumnModel().getColumn(6).setPreferredWidth(60);
        
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        
        // Info Panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(new Color(236, 240, 241));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel infoLabel = new JLabel("💡 Tip: Select a student and click 'View Details' for more information");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setForeground(new Color(52, 73, 94));
        infoPanel.add(infoLabel);
        
        // Assemble panels
        JPanel topPanel = new JPanel(new BorderLayout(0, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(resultPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
        
        // Double-click to view details
        studentTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewStudentDetails();
                }
            }
        });
    }
    
    private void loadAllStudents() {
        tableModel.setRowCount(0);
        List<Student> students = studentDAO.getAllStudents();
        
        for (Student student : students) {
            Object[] row = {
                student.getStudentId(),
                student.getRegistrationNumber(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getDepartment(),
                student.getYearOfStudy()
            };
            tableModel.addRow(row);
        }
        
        lblResultCount.setText("Total students: " + students.size());
    }
    
    private void searchStudents() {
        String searchTerm = txtSearch.getText().trim();
        
        if (searchTerm.isEmpty()) {
            loadAllStudents();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Student> students = studentDAO.searchStudents(searchTerm);
        
        for (Student student : students) {
            Object[] row = {
                student.getStudentId(),
                student.getRegistrationNumber(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getDepartment(),
                student.getYearOfStudy()
            };
            tableModel.addRow(row);
        }
        
        lblResultCount.setText("Found " + students.size() + " student(s)");
        
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "No students found matching: " + searchTerm,
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    private void clearSearch() {
        txtSearch.setText("");
        loadAllStudents();
    }
    
    private void viewStudentDetails() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Please select a student to view details!",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        int studentId = (int) tableModel.getValueAt(selectedRow, 0);
        Student student = studentDAO.getStudentById(studentId);
        
        if (student != null) {
            String details = String.format(
                "═══════════════════════════════════════\n" +
                "           STUDENT DETAILS\n" +
                "═══════════════════════════════════════\n\n" +
                "Student ID:          %d\n" +
                "Registration No:     %s\n" +
                "Full Name:           %s %s\n" +
                "Email:               %s\n" +
                "Phone:               %s\n" +
                "Department:          %s\n" +
                "Year of Study:       %d\n\n" +
                "═══════════════════════════════════════",
                student.getStudentId(),
                student.getRegistrationNumber(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail() != null ? student.getEmail() : "N/A",
                student.getPhone() != null ? student.getPhone() : "N/A",
                student.getDepartment(),
                student.getYearOfStudy()
            );
            
            JTextArea textArea = new JTextArea(details);
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            
            JOptionPane.showMessageDialog(
                this,
                textArea,
                "Student Information",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}