package Test;

import javax.swing.*;
import java.text.DateFormat;
import java.util.Date;

public class UserFaker extends JFrame {
    public UserFaker() {
        JFormattedTextField tft2 = new JFormattedTextField(DateFormat.getDateInstance(DateFormat.SHORT));
        tft2.setValue(new Date());
        this.add(tft2);
        tft2.setBounds(0,0,200,200);
        this.setLayout(null);
        this.setSize(200,200);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new UserFaker();
    }
}
