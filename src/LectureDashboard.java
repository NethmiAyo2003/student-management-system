// src/LecturerDashboard.java

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LectureDashboard extends JFrame {
    
    private int lecturerId;
    private String lecturerName;
    
    // UI Components
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    
    // Buttons
    private JButton btnLectureSlides;
    private JButton btnStudentAverage;
    private JButton btnTimetable;
    private JButton btnStudentSearch;
    private JButton btnLogout;
    
    public LectureDashboard(int lecturerId, String lecturerName) {
        this.lecturerId = lecturerId;
        this.lecturerName = lecturerName;
        
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        setTitle("Lecturer Dashboard - " + lecturerName);
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Main Panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Sidebar Panel
        createSidebar();
        
        // Content Panel
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Welcome Panel (Default)
        showWelcomePanel();
        
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(41, 128, 185));
        sidebarPanel.setPreferredSize(new Dimension(250, getHeight()));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        // Header
        JLabel headerLabel = new JLabel("LECTURER PORTAL");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(headerLabel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JLabel nameLabel = new JLabel(lecturerName);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(nameLabel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Menu Buttons
        btnLectureSlides = createMenuButton("📚 Lecture Slides");
        btnStudentAverage = createMenuButton("📊 Student Average");
        btnTimetable = createMenuButton("📅 Timetable");
        btnStudentSearch = createMenuButton("🔍 Student Search");
        btnLogout = createMenuButton("🚪 Logout");
        
        sidebarPanel.add(btnLectureSlides);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnStudentAverage);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnTimetable);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnStudentSearch);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        sidebarPanel.add(btnLogout);
        
        // Button Actions
        btnLectureSlides.addActionListener(e -> showLectureSlidesPanel());
        btnStudentAverage.addActionListener(e -> showStudentAveragePanel());
        btnTimetable.addActionListener(e -> showTimetablePanel());
        btnStudentSearch.addActionListener(e -> showStudentSearchPanel());
        btnLogout.addActionListener(e -> logout());
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(230, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(41, 128, 185));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(52, 152, 219));
            }
        });
        
        return button;
    }
    
    private void showWelcomePanel() {
        contentPanel.removeAll();
        
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setBackground(Color.WHITE);
        
        JLabel welcomeLabel = new JLabel("Welcome, " + lecturerName + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(41, 128, 185));
        
        welcomePanel.add(welcomeLabel);
        
        contentPanel.add(welcomePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showLectureSlidesPanel() {
        contentPanel.removeAll();
        LectureSlidePanel slidesPanel = new LectureSlidePanel(lecturerId);
        contentPanel.add(slidesPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showStudentAveragePanel() {
        contentPanel.removeAll();
        StudentAveragePanel averagePanel = new StudentAveragePanel(lecturerId);
        contentPanel.add(averagePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showTimetablePanel() {
        contentPanel.removeAll();
        TimeabalePanel timetablePanel = new TimeabalePanel(lecturerId);
        contentPanel.add(timetablePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showStudentSearchPanel() {
        contentPanel.removeAll();
        StudentSearchPanel searchPanel = new StudentSearchPanel();
        contentPanel.add(searchPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            // Here you would open your login page
            JOptionPane.showMessageDialog(null, "Logged out successfully!");
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Test with sample lecturer
            LectureDashboard dashboard = new LectureDashboard(1, "Dr. John Smith");
            dashboard.setVisible(true);
        });
    }
}