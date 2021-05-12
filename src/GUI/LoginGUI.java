package GUI;

import BLL.LoginBLL;
import GUI.Layout.LoginLayout;

public class LoginGUI {
    LoginLayout loginLayout;

    public void hide() {
        this.loginLayout.setVisible(false);
    }

    public void show() {
        this.loginLayout.setVisible(true);
    }

    public void dispose() {
        loginLayout.dispose();
    }

    public LoginGUI(DashboardGUI dashboardGUI) {
        loginLayout = new LoginLayout((username, password) -> {
            
            if(LoginBLL.login(username,password)) {
                //JOptionPane.showMessageDialog(loginLayout,"OK");
                dispose();
                dashboardGUI.show();
                dispose();
            }
        });
    }

}
