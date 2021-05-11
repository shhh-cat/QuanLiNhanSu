package BLL;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ResponseType {
    public static final int ERROR_SQL = -2;
    public static final int INVALID_DATA = 0;
    public static final int SUCCESS = 1;
    public static final int FAILED = -1;

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    public static boolean showResponse(Map<Integer, Object> res, Component parentComponent) {
        for (Map.Entry<Integer, Object> integerObjectEntry : res.entrySet()) {
            switch (integerObjectEntry.getKey()) {
                case ResponseType.SUCCESS:
                    JOptionPane.showMessageDialog(parentComponent,integerObjectEntry.getValue().toString(),"SUCCESS",JOptionPane.INFORMATION_MESSAGE);
                    return true;
                case ResponseType.ERROR_SQL:
                    JOptionPane.showMessageDialog(parentComponent,integerObjectEntry.getValue().toString(),"ERROR SQL",JOptionPane.ERROR_MESSAGE);
                    return false;
                case ResponseType.FAILED:
                    JOptionPane.showMessageDialog(parentComponent,integerObjectEntry.getValue().toString(),"FAILED",JOptionPane.ERROR_MESSAGE);
                    return false;
                case ResponseType.INVALID_DATA:
                    JOptionPane.showMessageDialog(parentComponent,integerObjectEntry.getValue().toString(),"INVALID DATA",JOptionPane.WARNING_MESSAGE);
                    return false;
                default:
                    break;
            }
        }
        return false;
    }

}
