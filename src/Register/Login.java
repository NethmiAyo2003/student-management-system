
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {

    JComboBox<String> role;
    JTextField email;
    JPasswordField password;

    public Login() {
        setTitle("Login");
        setSize(400,300);
        setLayout(new GridLayout(5,2,10,10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        role = new JComboBox<>(new String[]{"Student","Lecturer","Admin"});
        email = new JTextField();
        password = new JPasswordField();

        JButton login = new JButton("Login");
        JButton register = new JButton("Register");

        add(new JLabel("Role")); add(role);
        add(new JLabel("Email")); add(email);
        add(new JLabel("Password")); add(password);
        add(login); add(register);

        login.addActionListener(e -> loginUser());
        register.addActionListener(e -> { dispose(); new Register(); });

        setVisible(true);
    }

    private void loginUser() {
        try {
            Connection con = DBUtil.getConnection();
            String table = role.getSelectedItem().toString().toLowerCase()+"s";

            String sql = "select * from "+table+" where email=? and password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email.getText());
            ps.setString(2, new String(password.getPassword()));

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                dispose();
                if(table.equals("students")) {
                    // Create Student object and open Student Dashboard
                    sms.model.Student student = new sms.model.Student();
                    student.setId(rs.getInt("student_id"));
                    student.setName(rs.getString("name"));
                    student.setEmail(rs.getString("email"));
                    student.setPhone(rs.getString("phone"));
                    student.setGender(rs.getString("gender"));
                    student.setDob(rs.getString("dob"));
                    student.setDegree(rs.getString("degree"));
                    student.setFaculty(rs.getString("faculty"));
                    student.setEnrollmentYear(rs.getInt("enrollment_year"));
                    student.setAddress(rs.getString("address"));
                    new sms.ui.Dashboard(student).setVisible(true);
                }
                else if(table.equals("lecturers")) {
                    int lecturerId = rs.getInt("lecturer_id");
                    String lecturerName = rs.getString("name");
                    new LectureDashboard(lecturerId, lecturerName).setVisible(true);
                }
                else {
                    new AdminDashboard().setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this,"Invalid Login");
            }
            con.close();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
