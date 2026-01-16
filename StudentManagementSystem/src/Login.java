import javax.swing.*;
import java.awt.*;
import java.sql.*;
import sms.ui.Dashboard;


public class Login extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("Student Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        panel.add(loginBtn);

        loginBtn.addActionListener(e -> login());

        add(panel);
    }

    private void login() {
        try {
            Connection conn = DB.getConnection();
            String sql = "SELECT * FROM students WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, usernameField.getText());
            stmt.setString(2, String.valueOf(passwordField.getPassword()));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("student_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("course"),
                        rs.getDouble("gpa"),
                        rs.getInt("credits"),
                        rs.getString("photo")
                );

                new Dashboard(null).setVisible(true);
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid login!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

