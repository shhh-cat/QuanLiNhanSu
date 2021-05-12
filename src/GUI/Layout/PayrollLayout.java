package GUI.Layout;

import GUI.Ultilities.FieldPanel;
import GUI.Ultilities.FontCustom;

import javax.swing.*;
import java.awt.*;

public class PayrollLayout extends JFrame{

    Font font;
    JPanel gui = new JPanel(new BorderLayout(5,5));
    JPanel left = new JPanel(new GridLayout(0,1,3,3));
    JPanel right = new JPanel(new GridLayout(0,1,3,3));
    JPanel emplName = new FieldPanel("Employee name: ",FieldPanel.TEXT_FIELD,null,new Dimension(200,35),new GridLayout(0,1,0,0),true,font);
    JPanel deptName = new FieldPanel("Department: ",FieldPanel.TEXT_FIELD,null,new Dimension(200,35),new GridLayout(0,1,0,0),true,font);

    public PayrollLayout() {
        font =  font = FontCustom.getFont("OpenSans",FontCustom.SEMI_BOLD_ITALIC,20);
        addcomp();
        configuration();
        listener();
    }

    void listener() {

    }

    void configuration() {

    }

    void addcomp(){
        gui.add(left,BorderLayout.WEST);
        gui.add(right,BorderLayout.CENTER);
        this.setContentPane(gui);
    }

}
