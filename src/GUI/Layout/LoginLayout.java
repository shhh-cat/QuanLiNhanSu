package GUI.Layout;

import GUI.ActionInterface.LoginAction;
import GUI.LoginGUI;
import GUI.Ultilities.HighlightButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginLayout extends JFrame implements ActionListener, KeyListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        loginAction.submit(tUser.getText(),tPass.getText());
    }


    LoginAction loginAction;
    JPanel panel = new JPanel();
    JPanel ipanel = new JPanel();
    JPanel ipanel2 = new JPanel();
    JLabel lUser = new JLabel("Enter username:"), lPass = new JLabel("Enter Password:");
    JTextField tUser = new JTextField(), tPass = new JPasswordField();
    HighlightButton bSubmit = new HighlightButton("Login");

    public void addComp() {
        ipanel.add(lUser);ipanel.add(tUser);ipanel.add(lPass);ipanel.add(tPass);
        ipanel2.add(bSubmit);
        panel.add(ipanel);
        panel.add(ipanel2);
        this.add(panel);
    }

    public void config() {
        panel.setBorder(BorderFactory.createTitledBorder("Login Panel"));
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setSize(200,50);
        ipanel.setLayout(new GridLayout(2,2,1,15));
        ipanel2.setLayout(new FlowLayout());
        ipanel.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        ipanel2.setBorder(BorderFactory.createEmptyBorder(0,0,15,0));

        tUser.setPreferredSize(new Dimension(150,25));
        tPass.setPreferredSize(new Dimension(150,25));

        //bSubmit.setMargin(new Insets(10,10,10,10));
        bSubmit.setHighlight(new Color(2, 117, 216,75));
        bSubmit.setPreferredSize(new Dimension(70,30));
        bSubmit.setBounds(0,0,50,20);


        this.setTitle("Login");
        this.pack();

        this.setResizable(false);
        try {
            // 1.6+
            this.setLocationByPlatform(true);
            this.setMinimumSize(this.getSize());
        } catch(Throwable ignoreAndContinue) {
        }
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void listener() {
        tUser.addKeyListener(this);
        tPass.addKeyListener(this);
        bSubmit.addActionListener(this);
    }

    public LoginLayout(LoginAction loginAction) {
        this.loginAction = loginAction;
        this.addComp();
        this.config();
        this.listener();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            loginAction.submit(tUser.getText(),tPass.getText());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
