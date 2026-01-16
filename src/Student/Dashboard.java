package sms.ui;

import sms.db.DB;
import sms.db.DBConnection;
import sms.model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dashboard extends JFrame {

    private final Student student;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);

    public Dashboard(Student student) {
        this.student = student;

        setTitle("Student Dashboard");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        contentPanel.add(homePanel(), "HOME");
        contentPanel.add(profilePanel(), "PROFILE");
        contentPanel.add(coursesPanel(), "COURSES");
        contentPanel.add(attendancePanel(), "ATTENDANCE");
        contentPanel.add(gradesPanel(), "GRADES");
        contentPanel.add(paymentsPanel(), "PAYMENTS");

        cardLayout.show(contentPanel, "HOME");
    }

    // ================= LEFT SIDEBAR (UNCHANGED) =================
    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(10, 30, 75));
        sidebar.setPreferredSize(new Dimension(280, 700));
        sidebar.setLayout(new GridLayout(8, 1, 0, 12));
        sidebar.setBorder(new EmptyBorder(25, 15, 25, 15));

        JLabel title = new JLabel("STUDENT PORTAL", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sidebar.add(title);

        sidebar.add(sideButton("HOME", "HOME"));
        sidebar.add(sideButton("PROFILE", "PROFILE"));
        sidebar.add(sideButton("COURSES", "COURSES"));
        sidebar.add(sideButton("ATTENDANCE", "ATTENDANCE"));
        sidebar.add(sideButton("GRADES", "GRADES"));
        sidebar.add(sideButton("PAYMENTS", "PAYMENTS"));

        JButton logout = new JButton("LOGOUT");
        logout.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logout.setBackground(new Color(180, 60, 60));
        logout.setForeground(Color.WHITE);
        logout.setFocusPainted(false);
        logout.addActionListener(e -> logout());
        sidebar.add(logout);

        return sidebar;
    }

    private JButton sideButton(String text, String card) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(new Color(25, 60, 130));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.addActionListener(e -> cardLayout.show(contentPanel, card));
        return btn;
    }

    // ================= RIGHT SIDE (BIGGER FONTS) =================

    private JPanel homePanel() {
        JPanel bg = basePanel();
        bg.setLayout(new GridLayout(2, 2, 25, 25));

        bg.add(infoCard("Student Name", student.getName()));
        bg.add(infoCard("Student ID", String.valueOf(student.getId())));
        bg.add(infoCard("Degree Program", student.getDegree()));
        bg.add(infoCard("Faculty", student.getFaculty()));

        return bg;
    }

    private JPanel profilePanel() {
        return sectionPanel(
                "Student Profile",
                "Name: " + student.getName() +
                "\nEmail: " + student.getEmail() +
                "\nPhone: " + student.getPhone() +
                "\nGender: " + student.getGender() +
                "\nDate of Birth: " + student.getDob() +
                "\nDegree: " + student.getDegree() +
                "\nFaculty: " + student.getFaculty() +
                "\nEnrollment Year: " + student.getEnrollmentYear() +
                "\nAddress: " + student.getAddress()
        );
    }

    private JPanel coursesPanel() {
        return sectionPanel("Enrolled Courses", DB.getEnrolledCourses(student.getId()));
    }

    private JPanel attendancePanel() {
        return sectionPanel("Attendance Overview", DB.getAttendance(student.getId()));
    }

    private JPanel gradesPanel() {
        return sectionPanel("Grades & GPA", DB.getGrades(student.getId()));
    }

    private JPanel paymentsPanel() {
        return sectionPanel("Payment History", loadPayments());
    }

    // ================= UI HELPERS (RIGHT SIDE ONLY) =================

    private JPanel basePanel() {
        JPanel p = new JPanel();
        p.setBackground(new Color(245, 247, 250));
        p.setBorder(new EmptyBorder(35, 35, 35, 35));
        return p;
    }

    private JPanel infoCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(new Color(220, 220, 220), 1));

        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(new Font("Segoe UI", Font.BOLD, 20));
        t.setBorder(new EmptyBorder(15, 10, 10, 10));

        JLabel v = new JLabel(value, SwingConstants.CENTER);
        v.setFont(new Font("Segoe UI", Font.BOLD, 28));
        v.setForeground(new Color(41, 128, 185));

        card.add(t, BorderLayout.NORTH);
        card.add(v, BorderLayout.CENTER);
        return card;
    }

    private JPanel sectionPanel(String title, String content) {
        JPanel bg = basePanel();
        bg.setLayout(new BorderLayout());

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(new Color(220, 220, 220), 1));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setBorder(new EmptyBorder(20, 20, 15, 20));

        JTextArea area = new JTextArea(content);
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(null);

        card.add(heading, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        bg.add(card);

        return bg;
    }

    // ================= PAYMENTS =================
    private String loadPayments() {
        StringBuilder sb = new StringBuilder();

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                    "SELECT amount, date FROM payments WHERE student_id = ?");
            pst.setInt(1, student.getId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                sb.append("Rs. ")
                  .append(rs.getDouble("amount"))
                  .append("  |  ")
                  .append(rs.getString("date"))
                  .append("\n");
            }
        } catch (Exception e) {
            return "Error loading payment data.";
        }

        return sb.length() == 0 ? "No payment records found." : sb.toString();
    }

    // ================= LOGOUT =================
    private void logout() {
        dispose();
        System.exit(0);
    }
}
