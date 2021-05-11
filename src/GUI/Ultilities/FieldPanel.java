package GUI.Ultilities;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;


public class FieldPanel extends JPanel{
    public static final int TEXT_FIELD = 1;
    public static final int COMBO_BOX = 2;
    public static final int DATE_PICKER = 3;
    private Component input;
    private String nameField;

    public String getNameField() {
        return nameField;
    }

    public Component getInput() {
        return input;
    }

    public FieldPanel(String lb, int type, String[] content, Dimension dimension, LayoutManager layoutManager, boolean isParentLayout, Font font) {
        this.nameField = lb;
   //     if (isParentLayout)
//            this.setPreferredSize(dimension);
//        else
//            this.setSize(dimension);
        this.setLayout(layoutManager);
        this.setBackground(new Color(0,0,0,5));
        JLabel jLabel = new JLabel(lb);
        if (font != null) {
            jLabel.setFont(font);;
        }
        this.add(jLabel);
        switch (type) {
            case 1:
                JTextField tf = new JTextField();
                if (layoutManager != null) {
                    tf.setPreferredSize(dimension);
                    tf.setMaximumSize(dimension);
                    if (lb.equals("Id")) tf.setEditable(false);
                }
                if (font != null)
                    tf.setFont(font);
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
            case 3:
                UtilDateModel model = new UtilDateModel();
                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                JDatePanelImpl jDatePanel = new JDatePanelImpl(model,p);
                JDatePickerImpl jDatePicker = new JDatePickerImpl(jDatePanel, new DateLabelFormatter());
                if (layoutManager != null) {
                    int height = dimension.height;
                    //jDatePanel.setPreferredSize(dimension);
                   jDatePicker.getComponent(0).setPreferredSize(dimension);

//                    jDatePicker.getJFormattedTextField().setMargin(new Insets(0,0,0,0));
                    jDatePicker.getComponent(1).setPreferredSize(new Dimension(height,height));
                    jDatePicker.getComponent(0).setFont(font);
                    jDatePicker.setBackground(Color.WHITE);
//                    jDatePicker.getComponent(0).setMinimumSize(jDatePicker.getPreferredSize());
//                    jDatePicker.getJFormattedTextField().setHorizontalAlignment(SwingConstants.CENTER);
                }
                this.add(jDatePicker);
                this.input = jDatePicker;
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

