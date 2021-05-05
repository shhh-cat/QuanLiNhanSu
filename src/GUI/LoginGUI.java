package GUI;

import BLL.LoginBLL;
import GUI.ActionInterface.LoginAction;
import GUI.Layout.LoginLayout;

import javax.swing.*;

public class LoginGUI {
    LoginLayout loginLayout;

    public LoginGUI() {
        loginLayout = new LoginLayout(new LoginAction() {
            @Override
            public void submit(String username, String password) {
                if(LoginBLL.login(username,password)) JOptionPane.showMessageDialog(loginLayout,"OK");
            }
        });
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
