package BLL;

import GUI.Layout.LoginLayout;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.util.Arrays;

public class LoginBLL {
    public static boolean login(String username, String password) {
        String[][] check = UserBLL.search("username",username);
        if (check.length > 0) {
            if (BCrypt.checkpw(password, check[0][2])) return true;
            JOptionPane.showMessageDialog(null,"Username or password is invalid","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        JOptionPane.showMessageDialog(null,"Username or password is invalid","Error",JOptionPane.ERROR_MESSAGE);
        return false;
    }

}
