import com.formdev.flatlaf.FlatLightLaf;
import sms.ui.LoginUI;
import sms.ui.Dashboard;


public class App {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        new LoginUI().setVisible(true);
    }
}
