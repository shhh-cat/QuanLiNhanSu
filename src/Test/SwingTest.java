package Test;

import GUI.Ultilities.FieldPanel;

import javax.swing.*;
import java.awt.*;


public class SwingTest extends JFrame{
    public SwingTest(){
        //this.add(FieldPanel.getFieldPanel("Ahihi",FieldPanel.TEXT_FIELD,new Dimension(200,200),new BoxLayout(this, BoxLayout.Y_AXIS),false));
        this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        //Connector connector = new Connector();
//        Employee e = new Employee("ahihi","nguyen","hihuhu","269 Ba Hom","P.13","Q.6","VietNam",false,"Kinh","Không","079201003982");
//        Employee.insert(e);
//        Map<String,Object> update = new HashMap<>();
//        update.put("firstname","ahihi");
//        Employee.update(update,3);
//        Employee.getAhihi().forEach((employee -> {
//            System.out.println(employee.toString());
//        }));
    new SwingTest();
    }
}
