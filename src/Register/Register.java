
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Register extends JFrame {

    JComboBox<String> role;
    JComboBox<String> gender;
    JComboBox<String> faculty;
    JTextField name,email,phone,field1,address,password,repassword;
    JSpinner dob;
    JSpinner year;

    public Register(){
        setTitle("Register");
        setSize(500,500);
        setLayout(new GridLayout(14,2,5,5));
        setLocationRelativeTo(null);

        role = new JComboBox<>(new String[]{"Student","Lecturer","Admin"});
        faculty = new JComboBox<>(new String[]{"Computing", "Buisness", "Engineeering", "Medicine"});
        gender = new JComboBox<>(new String[]{"Male","Female","Other"});
        name=new JTextField(); email=new JTextField(); phone=new JTextField(); dob=new JSpinner(new SpinnerDateModel()); field1=new JTextField();
        address=new JTextField(); password=new JTextField(); repassword=new JTextField();

        dob = new JSpinner(new SpinnerDateModel());
        year = new JSpinner(new SpinnerNumberModel(2024, 2000, 2100, 1));
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dob, "yyyy-MM-dd");
        JSpinner.NumberEditor yearEditor = new JSpinner.NumberEditor(year, "#");
        year.setEditor(yearEditor);
        dob.setEditor(editor);

        JPanel dobPanel = new JPanel(new BorderLayout(5,0));
        dobPanel.add(dob, BorderLayout.CENTER);

        add(new JLabel("Role")); add(role);
        add(new JLabel("Name")); add(name);
        add(new JLabel("Email")); add(email);
        add(new JLabel("Phone")); add(phone);
        add(new JLabel("Gender")); add(gender);
        add(new JLabel("DOB (YYYY-MM-DD)")); add(dob);
        add(new JLabel("Degree / Subject")); add(field1);
        add(new JLabel("Faculty")); add(faculty);
        add(new JLabel("Enrollment Year")); add(year);
        add(new JLabel("Address")); add(address);
        add(new JLabel("Password")); add(password);
        add(new JLabel("Confirm Password")); add(repassword);
        add(dobPanel);

        JButton save = new JButton("Register");
        add(save);

        save.addActionListener(e -> saveUser());

        setVisible(true);
    }

    private void saveUser(){
        try{
            Connection con = DBUtil.getConnection();
            String r = role.getSelectedItem().toString();

            String sql;
            if(r.equals("Student"))
                sql="INSERT INTO students(name,email,phone,gender,dob,degree,faculty,enrollment_year,address,password) VALUES(?,?,?,?,?,?,?,?,?,?)";
            else if(r.equals("Lecturer"))
                sql="INSERT INTO lecturers(name,email,phone,gender,dob,subject,faculty,enrollment_year,address,password) VALUES(?,?,?,?,?,?,?,?,?,?)";
            else
                sql="INSERT INTO admins(name,email,phone,gender,dob,faculty,enrollment_year,address,password) VALUES(?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,name.getText());
            ps.setString(2,email.getText());
            ps.setString(3,phone.getText());
            ps.setString(4,gender.getSelectedItem().toString());
            ps.setString(5,new java.text.SimpleDateFormat("yyyy-MM-dd").format((java.util.Date)dob.getValue()));
            ps.setString(6,field1.getText());
            ps.setString(7,faculty.getSelectedItem().toString());
            ps.setString(8,year.getValue().toString());
            ps.setString(9,address.getText());
            if(!r.equals("Admin")) ps.setString(10,password.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Registered Successfully");
            con.close();
            dispose(); new Login();

        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,ex.getMessage());
        }
    }
}
