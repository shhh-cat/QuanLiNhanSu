package GUI.Ultilities;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


public class FieldPanel extends JPanel {
    public static final int TEXT_FIELD = 1;
    public static final int COMBO_BOX = 2;
    private Component input;
    private String nameField;

    public String getNameField() {
        return nameField;
    }

    public Component getInput() {
        return input;
    }

    public FieldPanel(String lb, int type, String[] content, Dimension dimension, LayoutManager layoutManager, boolean isParentLayout) {
        this.nameField = lb;
        if (isParentLayout)
            this.setPreferredSize(dimension);
        else
            this.setSize(dimension);
        this.setLayout(layoutManager);
        this.add(new JLabel(lb));
        switch (type) {
            case 1:
                JTextField tf = new JTextField();
                if (layoutManager != null) {
                    tf.setPreferredSize(dimension);
                    tf.setMaximumSize(dimension);
                    if (lb.equals("id")) tf.setEditable(false);
                }
                this.add(tf);
                this.input = tf;
                break;
            case 2:
                JComboBox<String> cb = new JComboBox<>(content);
                if (layoutManager != null) {
                    cb.setPreferredSize(dimension);
                    cb.setMaximumSize(dimension);
                    if (lb.equals("id")) cb.setEditable(false);
                }
                this.add(cb);
                this.input = cb;
                break;
            default:
                break;
        }
    }
    public FieldPanel(String lb, int type,String[] content) {
        this.nameField = lb;
        this.setLayout(new BorderLayout(3,3));
        this.add(new JLabel(lb),BorderLayout.NORTH);
        switch (type) {
            case 1:
                JTextField tf = new JTextField();
                this.add(tf,BorderLayout.CENTER);
                input = tf;
                break;
            case 2:
                JComboBox<String> cb = new JComboBox<>(content);
                this.add(cb,BorderLayout.CENTER);
                input = cb;
                break;
            default:
                break;
        }
    }
}
