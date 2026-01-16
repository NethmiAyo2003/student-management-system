package sms.ui;

import sms.db.DBConnection;
import sms.model.Student;

import javax.swing.*;
import java.sql.*;

public class LoginUI extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginUI() {
        setTitle("Student Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(40, 40, 100, 25);
        panel.add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 40, 180, 25);
        panel.add(txtUsername);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(40, 80, 100, 25);
        panel.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 80, 180, 25);
        panel.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 130, 100, 30);
        panel.add(btnLogin);

        btnLogin.addActionListener(e -> login());

        add(panel);
    }

    // -------- LOGIN METHOD (THIS FIXES ID = 0 ISSUE) --------
    private void login() {
        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(
                "SELECT * FROM students WHERE username=? AND password=?"
            );

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Student student = new Student();

                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setDegree(rs.getString("degree"));
                student.setFaculty(rs.getString("faculty"));
                student.setEnrollmentYear(rs.getInt("enrollment_year"));
                student.setGender(rs.getString("gender"));
                student.setDob(rs.getString("dob"));
                student.setAddress(rs.getString("address"));

                System.out.println("Logged Student ID = " + student.getId());

                new Dashboard(student).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
