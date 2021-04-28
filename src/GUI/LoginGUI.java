package GUI;

import GUI.ActionInterface.LoginAction;
import GUI.Layout.LoginLayout;

import javax.swing.*;

public class LoginGUI {
    LoginLayout loginLayout;

    public LoginGUI() {
        loginLayout = new LoginLayout(new LoginAction() {
            @Override
            public void submit(String username, String password) {
                JOptionPane.showMessageDialog(null,username);
                JOptionPane.showMessageDialog(null,password);
            }
        });
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
