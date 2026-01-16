
import javax.swing.*;
public class DashboardStudent extends JFrame{
    public DashboardStudent(){
        setTitle("Student Dashboard");
        setSize(400,200);
        add(new JLabel("Welcome Student",SwingConstants.CENTER));
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
