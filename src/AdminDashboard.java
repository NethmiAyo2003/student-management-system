import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class AdminDashboard extends JFrame {

    // ====== DATA MODELS ======
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Teacher> teachers = new ArrayList<>();

    // ====== UI PANELS ======
    private JPanel contentPanel;

    // ====== UITheme ======
    public static class UITheme {
        public static final Color BG_MAIN = new Color(0, 51, 102);
        public static final Color BG_MENU = new Color(0, 0, 51);
        public static final Color FIELD_BG = new Color(230, 230, 230);
        public static final Color TEXT_WHITE = Color.WHITE;
        public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
        public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
        public static final Font MENU_FONT = new Font("Segoe UI", Font.PLAIN, 15);
    }

    // ====== MYSQL CONNECT ======
    private Connection connectDB() {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/testdb";
            String user = "root"; // ඔබේ MySQL username
            String password = ""; // ඔබේ MySQL password
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("MySQL Connected Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("MySQL Connection Failed!");
        }
        return conn;
    }

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // LEFT MENU
        JPanel menu = new JPanel(new GridLayout(6,1,0,10));
        menu.setBackground(UITheme.BG_MENU);
        menu.setPreferredSize(new Dimension(260,0));

        JLabel admin = new JLabel("Admin", JLabel.CENTER);
        admin.setForeground(UITheme.TEXT_WHITE);
        admin.setFont(UITheme.TITLE_FONT);

        JButton btnAddStudent = menuButton("Add Student");
        JButton btnStudentDetails = menuButton("Student Details");
        JButton btnAddTeacher = menuButton("Add Teacher");
        JButton btnTeacherDetails = menuButton("Teacher Details");

        menu.add(admin);
        menu.add(btnAddStudent);
        menu.add(btnStudentDetails);
        menu.add(btnAddTeacher);
        menu.add(btnTeacherDetails);

        add(menu, BorderLayout.WEST);

        // CONTENT PANEL
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(UITheme.BG_MAIN);
        add(contentPanel, BorderLayout.CENTER);

        // default panel
        showPanel(new AddStudentPanel());

        // BUTTON ACTIONS
        btnAddStudent.addActionListener(e -> showPanel(new AddStudentPanel()));
        btnStudentDetails.addActionListener(e -> showPanel(new StudentDetailsPanel()));
        btnAddTeacher.addActionListener(e -> showPanel(new AddTeacherPanel()));
        btnTeacherDetails.addActionListener(e -> showPanel(new TeacherDetailsPanel()));

        setVisible(true);
    }

    private JButton menuButton(String text){
        JButton b = new JButton(text);
        b.setFont(UITheme.MENU_FONT);
        b.setForeground(UITheme.TEXT_WHITE);
        b.setBackground(UITheme.BG_MAIN);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));
        return b;
    }

    private void showPanel(JPanel p){
        contentPanel.removeAll();
        contentPanel.add(p);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ====== STUDENT & TEACHER CLASSES ======
    private static class Student {
        String id, firstName, middleName, lastName, gender, email, phone, dob, degree, faculty, enrollmentYear, parentName, parentPhone;
        Student(String id,String firstName,String middleName,String lastName,String gender,String email,String phone,String dob,String degree,String faculty,String enrollmentYear,String parentName,String parentPhone){
            this.id=id;this.firstName=firstName;this.middleName=middleName;this.lastName=lastName;this.gender=gender;
            this.email=email;this.phone=phone;this.dob=dob;this.degree=degree;this.faculty=faculty;this.enrollmentYear=enrollmentYear;
            this.parentName=parentName;this.parentPhone=parentPhone;
        }
        String fullName(){ return firstName+" "+middleName+" "+lastName; }
    }

    private static class Teacher {
        String id, firstName, middleName, lastName, gender, email, phone, dob, module, enrollmentYear;
        Teacher(String id,String firstName,String middleName,String lastName,String gender,String email,String phone,String dob,String module,String enrollmentYear){
            this.id=id;this.firstName=firstName;this.middleName=middleName;this.lastName=lastName;this.gender=gender;
            this.email=email;this.phone=phone;this.dob=dob;this.module=module;this.enrollmentYear=enrollmentYear;
        }
        String fullName(){ return firstName+" "+middleName+" "+lastName; }
    }

    // ====== ADD STUDENT PANEL ======
    private class AddStudentPanel extends JPanel{
        JTextField txtId, txtFirst, txtMiddle, txtLast, txtEmail, txtPhone, txtDOB, txtDegree, txtFaculty, txtEnrollment, txtParentName, txtParentPhone;
        JRadioButton male, female;
        JButton btnSave;
        public AddStudentPanel(){
            setBackground(UITheme.BG_MAIN);
            setLayout(new BorderLayout());

            JLabel title = new JLabel("Add Student");
            title.setFont(UITheme.TITLE_FONT);
            title.setForeground(UITheme.TEXT_WHITE);
            title.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));
            add(title, BorderLayout.NORTH);

            JPanel form = new JPanel(new GridBagLayout());
            form.setBackground(UITheme.BG_MAIN);
            form.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5,5,5,5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            int row=0;
            txtId = addField(form,"Student ID",row++);
            txtFirst = addField(form,"First Name",row++);
            txtMiddle = addField(form,"Middle Name",row++);
            txtLast = addField(form,"Last Name",row++);
            addGenderField(form,row++);
            txtEmail = addField(form,"Email",row++);
            txtPhone = addField(form,"Phone",row++);
            txtDOB = addField(form,"Date of Birth",row++);
            txtDegree = addField(form,"Degree",row++);
            txtFaculty = addField(form,"Faculty",row++);
            txtEnrollment = addField(form,"Enrollment Year",row++);
            txtParentName = addField(form,"Parent Name",row++);
            txtParentPhone = addField(form,"Parent Phone",row++);

            add(form,BorderLayout.CENTER);

            btnSave = new JButton("Save");
            styleButton(btnSave);
            JPanel p = new JPanel(); p.setBackground(UITheme.BG_MAIN); p.add(btnSave); add(p,BorderLayout.SOUTH);

            btnSave.addActionListener(e-> saveStudent(null));
        }

        private void saveStudent(Student editStudent){
            Connection conn = connectDB();
            if(editStudent == null){
                Student s = new Student(txtId.getText(), txtFirst.getText(), txtMiddle.getText(), txtLast.getText(),
                        male.isSelected()?"Male":"Female", txtEmail.getText(), txtPhone.getText(), txtDOB.getText(),
                        txtDegree.getText(), txtFaculty.getText(), txtEnrollment.getText(), txtParentName.getText(), txtParentPhone.getText());
                students.add(s);

                try {
                    String sql = "INSERT INTO students (id, firstName, middleName, lastName, gender, email, phone, dob, degree, faculty, enrollmentYear, parentName, parentPhone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, s.id); pst.setString(2, s.firstName); pst.setString(3, s.middleName); pst.setString(4, s.lastName);
                    pst.setString(5, s.gender); pst.setString(6, s.email); pst.setString(7, s.phone); pst.setString(8, s.dob);
                    pst.setString(9, s.degree); pst.setString(10, s.faculty); pst.setString(11, s.enrollmentYear);
                    pst.setString(12, s.parentName); pst.setString(13, s.parentPhone);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(this,"Student Added to DB!");
                } catch (SQLException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,"Error Adding Student to DB!");
                }
            }
            clearFields();
        }

        private JTextField addField(JPanel form,String label,int row){
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5,5,5,5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel l = new JLabel(label); l.setFont(UITheme.NORMAL_FONT); l.setForeground(UITheme.TEXT_WHITE);
            gbc.gridx=0; gbc.gridy=row; form.add(l,gbc);
            JTextField t = new JTextField(20); t.setFont(UITheme.NORMAL_FONT); t.setBackground(UITheme.FIELD_BG);
            gbc.gridx=1; form.add(t,gbc);
            return t;
        }

        private void addGenderField(JPanel form,int row){
            JLabel l = new JLabel("Gender"); l.setFont(UITheme.NORMAL_FONT); l.setForeground(UITheme.TEXT_WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5,5,5,5); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.gridx=0; gbc.gridy=row;
            form.add(l,gbc);

            male = new JRadioButton("Male"); male.setBackground(UITheme.BG_MAIN); male.setForeground(UITheme.TEXT_WHITE);
            female = new JRadioButton("Female"); female.setBackground(UITheme.BG_MAIN); female.setForeground(UITheme.TEXT_WHITE);
            ButtonGroup bg = new ButtonGroup(); bg.add(male); bg.add(female);
            JPanel p = new JPanel(); p.setBackground(UITheme.BG_MAIN); p.add(male); p.add(female);
            gbc.gridx=1; form.add(p,gbc);
        }

        private void styleButton(JButton b){ b.setBackground(UITheme.BG_MENU); b.setForeground(UITheme.TEXT_WHITE); b.setFocusPainted(false);}
        private void clearFields(){
            txtId.setText(""); txtFirst.setText(""); txtMiddle.setText(""); txtLast.setText("");
            txtEmail.setText(""); txtPhone.setText(""); txtDOB.setText(""); txtDegree.setText("");
            txtFaculty.setText(""); txtEnrollment.setText(""); txtParentName.setText(""); txtParentPhone.setText("");
            male.setSelected(false); female.setSelected(false);
        }
    }

    // ====== STUDENT DETAILS PANEL ======
    private class StudentDetailsPanel extends JPanel{
        JTable table; DefaultTableModel model;
        public StudentDetailsPanel(){
            setLayout(new BorderLayout()); setBackground(UITheme.BG_MAIN);
            JLabel title = new JLabel("Student Details"); title.setFont(UITheme.TITLE_FONT); title.setForeground(UITheme.TEXT_WHITE);
            title.setBorder(BorderFactory.createEmptyBorder(20,30,10,30)); add(title,BorderLayout.NORTH);

            JPanel search = new JPanel(); search.setBackground(UITheme.BG_MAIN);
            JTextField txtSearch = new JTextField(20); JButton btnSearch = new JButton("Search"); styleButton(btnSearch);
            JButton btnEdit = new JButton("Edit"); JButton btnDelete = new JButton("Delete"); styleButton(btnEdit); styleButton(btnDelete);
            search.add(new JLabel("ID / Name")); search.add(txtSearch); search.add(btnSearch); search.add(btnEdit); search.add(btnDelete);
            add(search,BorderLayout.CENTER);

            String[] cols={"ID","Name","Degree","DOB","Email"};
            model = new DefaultTableModel(cols,0);
            table = new JTable(model); add(new JScrollPane(table),BorderLayout.SOUTH);

            refreshTable("");

            btnSearch.addActionListener(e-> refreshTable(txtSearch.getText()));

            btnDelete.addActionListener(e-> {
                int row = table.getSelectedRow();
                if(row>=0){
                    students.remove(row);
                    refreshTable(txtSearch.getText());
                    JOptionPane.showMessageDialog(null,"Student Deleted!");
                }
            });

            btnEdit.addActionListener(e-> {
                int row = table.getSelectedRow();
                if(row>=0){
                    Student s = students.get(row);
                    AddStudentPanel panel = new AddStudentPanel();
                    panel.txtId.setText(s.id); panel.txtFirst.setText(s.firstName); panel.txtMiddle.setText(s.middleName);
                    panel.txtLast.setText(s.lastName); panel.txtEmail.setText(s.email); panel.txtPhone.setText(s.phone);
                    panel.txtDOB.setText(s.dob); panel.txtDegree.setText(s.degree); panel.txtFaculty.setText(s.faculty);
                    panel.txtEnrollment.setText(s.enrollmentYear); panel.txtParentName.setText(s.parentName);
                    panel.txtParentPhone.setText(s.parentPhone);
                    if(s.gender.equals("Male")) panel.male.setSelected(true); else panel.female.setSelected(true);
                    panel.btnSave.addActionListener(ev -> panel.saveStudent(s));
                    showPanel(panel);
                }
            });
        }

        private void refreshTable(String key){
            model.setRowCount(0);
            key = key.toLowerCase();
            for(Student s:students){
                if(s.id.toLowerCase().contains(key) || s.fullName().toLowerCase().contains(key)){
                    model.addRow(new Object[]{s.id,s.fullName(),s.degree,s.dob,s.email});
                }
            }
        }

        private void styleButton(JButton b){ b.setBackground(UITheme.BG_MENU); b.setForeground(UITheme.TEXT_WHITE); b.setFocusPainted(false);}
    }

    // ====== ADD TEACHER PANEL ======
    private class AddTeacherPanel extends JPanel{
        JTextField txtId, txtFirst, txtMiddle, txtLast, txtEmail, txtPhone, txtDOB, txtModule, txtEnrollment;
        JRadioButton male, female;
        JButton btnSave;
        public AddTeacherPanel(){
            setBackground(UITheme.BG_MAIN);
            setLayout(new BorderLayout());

            JLabel title = new JLabel("Add Teacher");
            title.setFont(UITheme.TITLE_FONT);
            title.setForeground(UITheme.TEXT_WHITE);
            title.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));
            add(title, BorderLayout.NORTH);

            JPanel form = new JPanel(new GridBagLayout());
            form.setBackground(UITheme.BG_MAIN);
            form.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5,5,5,5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            int row=0;
            txtId = addField(form,"Teacher ID",row++);
            txtFirst = addField(form,"First Name",row++);
            txtMiddle = addField(form,"Middle Name",row++);
            txtLast = addField(form,"Last Name",row++);
            addGenderField(form,row++);
            txtEmail = addField(form,"Email",row++);
            txtPhone = addField(form,"Phone",row++);
            txtDOB = addField(form,"Date of Birth",row++);
            txtModule = addField(form,"Module",row++);
            txtEnrollment = addField(form,"Enrollment Year",row++);

            add(form,BorderLayout.CENTER);

            btnSave = new JButton("Save");
            styleButton(btnSave);
            JPanel p = new JPanel(); p.setBackground(UITheme.BG_MAIN); p.add(btnSave); add(p,BorderLayout.SOUTH);

            btnSave.addActionListener(e-> saveTeacher(null));
        }

        private void saveTeacher(Teacher editTeacher){
            Connection conn = connectDB();
            if(editTeacher == null){
                Teacher t = new Teacher(txtId.getText(), txtFirst.getText(), txtMiddle.getText(), txtLast.getText(),
                        male.isSelected()?"Male":"Female", txtEmail.getText(), txtPhone.getText(), txtDOB.getText(),
                        txtModule.getText(), txtEnrollment.getText());
                teachers.add(t);

                try {
                    String sql = "INSERT INTO teachers (id, firstName, middleName, lastName, gender, email, phone, dob, module, enrollmentYear) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1,t.id); pst.setString(2,t.firstName); pst.setString(3,t.middleName); pst.setString(4,t.lastName);
                    pst.setString(5,t.gender); pst.setString(6,t.email); pst.setString(7,t.phone); pst.setString(8,t.dob);
                    pst.setString(9,t.module); pst.setString(10,t.enrollmentYear);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(this,"Teacher Added to DB!");
                } catch (SQLException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this,"Error Adding Teacher to DB!");
                }
            }
            clearFields();
        }

        private JTextField addField(JPanel form,String label,int row){
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5,5,5,5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel l = new JLabel(label); l.setFont(UITheme.NORMAL_FONT); l.setForeground(UITheme.TEXT_WHITE);
            gbc.gridx=0; gbc.gridy=row; form.add(l,gbc);
            JTextField t = new JTextField(20); t.setFont(UITheme.NORMAL_FONT); t.setBackground(UITheme.FIELD_BG);
            gbc.gridx=1; form.add(t,gbc);
            return t;
        }

        private void addGenderField(JPanel form,int row){
            JLabel l = new JLabel("Gender"); l.setFont(UITheme.NORMAL_FONT); l.setForeground(UITheme.TEXT_WHITE);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5,5,5,5); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.gridx=0; gbc.gridy=row;
            form.add(l,gbc);

            male = new JRadioButton("Male"); male.setBackground(UITheme.BG_MAIN); male.setForeground(UITheme.TEXT_WHITE);
            female = new JRadioButton("Female"); female.setBackground(UITheme.BG_MAIN); female.setForeground(UITheme.TEXT_WHITE);
            ButtonGroup bg = new ButtonGroup(); bg.add(male); bg.add(female);
            JPanel p = new JPanel(); p.setBackground(UITheme.BG_MAIN); p.add(male); p.add(female);
            gbc.gridx=1; form.add(p,gbc);
        }

        private void styleButton(JButton b){ b.setBackground(UITheme.BG_MENU); b.setForeground(UITheme.TEXT_WHITE); b.setFocusPainted(false);}
        private void clearFields(){
            txtId.setText(""); txtFirst.setText(""); txtMiddle.setText(""); txtLast.setText("");
            txtEmail.setText(""); txtPhone.setText(""); txtDOB.setText(""); txtModule.setText("");
            txtEnrollment.setText(""); male.setSelected(false); female.setSelected(false);
        }
    }

    // ====== TEACHER DETAILS PANEL ======
    private class TeacherDetailsPanel extends JPanel{
        JTable table; DefaultTableModel model;
        public TeacherDetailsPanel(){
            setLayout(new BorderLayout()); setBackground(UITheme.BG_MAIN);
            JLabel title = new JLabel("Teacher Details"); title.setFont(UITheme.TITLE_FONT); title.setForeground(UITheme.TEXT_WHITE);
            title.setBorder(BorderFactory.createEmptyBorder(20,30,10,30)); add(title,BorderLayout.NORTH);

            JPanel search = new JPanel(); search.setBackground(UITheme.BG_MAIN);
            JTextField txtSearch = new JTextField(20); JButton btnSearch = new JButton("Search"); styleButton(btnSearch);
            JButton btnEdit = new JButton("Edit"); JButton btnDelete = new JButton("Delete"); styleButton(btnEdit); styleButton(btnDelete);
            search.add(new JLabel("ID / Name")); search.add(txtSearch); search.add(btnSearch); search.add(btnEdit); search.add(btnDelete);
            add(search,BorderLayout.CENTER);

            String[] cols={"ID","Name","Module","DOB","Email"};
            model = new DefaultTableModel(cols,0);
            table = new JTable(model); add(new JScrollPane(table),BorderLayout.SOUTH);

            refreshTable("");

            btnSearch.addActionListener(e-> refreshTable(txtSearch.getText()));

            btnDelete.addActionListener(e-> {
                int row = table.getSelectedRow();
                if(row>=0){
                    teachers.remove(row);
                    refreshTable(txtSearch.getText());
                    JOptionPane.showMessageDialog(null,"Teacher Deleted!");
                }
            });

            btnEdit.addActionListener(e-> {
                int row = table.getSelectedRow();
                if(row>=0){
                    Teacher t = teachers.get(row);
                    AddTeacherPanel panel = new AddTeacherPanel();
                    panel.txtId.setText(t.id); panel.txtFirst.setText(t.firstName); panel.txtMiddle.setText(t.middleName);
                    panel.txtLast.setText(t.lastName); panel.txtEmail.setText(t.email); panel.txtPhone.setText(t.phone);
                    panel.txtDOB.setText(t.dob); panel.txtModule.setText(t.module); panel.txtEnrollment.setText(t.enrollmentYear);
                    if(t.gender.equals("Male")) panel.male.setSelected(true); else panel.female.setSelected(true);
                    panel.btnSave.addActionListener(ev -> panel.saveTeacher(t));
                    showPanel(panel);
                }
            });
        }

        private void refreshTable(String key){
            model.setRowCount(0);
            key = key.toLowerCase();
            for(Teacher t:teachers){
                if(t.id.toLowerCase().contains(key) || t.fullName().toLowerCase().contains(key)){
                    model.addRow(new Object[]{t.id,t.fullName(),t.module,t.dob,t.email});
                }
            }
        }

        private void styleButton(JButton b){ b.setBackground(UITheme.BG_MENU); b.setForeground(UITheme.TEXT_WHITE); b.setFocusPainted(false);}
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(AdminDashboard::new);
    }
}


