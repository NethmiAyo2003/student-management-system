
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
        JCheckBox showPassword = new JCheckBox("Show Password");

        add(new JLabel("Role")); add(role);
        add(new JLabel("Email")); add(email);
        add(new JLabel("Password")); add(password);
        add(new JLabel("")); add(showPassword);
        add(login); add(register);
        

        login.addActionListener(e -> loginUser());
        register.addActionListener(e -> { dispose(); new Register(); });
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                password.setEchoChar((char) 0); // show text
            } else {
                password.setEchoChar('*'); // hide text
            }
        });

        setVisible(true);
    }

    private void loginUser() {
        try {
            Connection con = DBUtil.getConnection();
            String table = role.getSelectedItem().toString()+"s";

            String sql = "SELECT * FROM "+table+" WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email.getText());
            ps.setString(2, new String(password.getPassword()));

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                dispose();
                if(table.equals("Students")) new DashboardStudent();
                else if(table.equals("Lecturers")) new DashboardLecturer();
                else new DashboardAdmin();
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
