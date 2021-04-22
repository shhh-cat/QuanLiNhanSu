package GUI;

import BLL.DTO.Employee;
import BLL.EmployeeBLL;
import BLL.ResponseType;
import DAL.EmployeeDAL;
import GUI.Layout.Action;
import GUI.Layout.ManagerLayout;
import GUI.Ultilities.FieldPanel;

import javax.swing.*;
import java.util.Map;

public class EmployeeGUI {
    private ManagerLayout managerLayout;
    public EmployeeGUI() {
        String[] columns = new String[] {
            "id",
            "firstname",
            "lastname",
            "phonenumber",
            "address",
            "ward",
            "district",
            "country",
            "gender",
            "ethnicity",
            "religion",
            "idnumber"
        };

        int[] types = new int[columns.length];
        String[][] typesContent = new String[columns.length][];
        for (int i = 0; i < columns.length; i++) {
            types[i] = (i != 8) ? FieldPanel.TEXT_FIELD : FieldPanel.COMBO_BOX;
            typesContent[i] = (i != 8) ? null : new String[] {"Nam", "Nữ"};
        }



        managerLayout = new ManagerLayout("Employee Manager", columns, types, typesContent, new Action() {
            @Override
            public void add(Map<String, Object> values) {
                Map<Integer, Object> res = EmployeeBLL.create(values);

                ResponseType.showResponse(res,managerLayout);

                managerLayout.setValueField(-1);
                managerLayout.updateTable(EmployeeBLL.all());
            }

            @Override
            public void update(Map<String, Object> newValues, int selectedRow, Map<String, Object> oldValues) {
                if (selectedRow < 0) JOptionPane.showMessageDialog(managerLayout,"Please select edit row!", "Warning", JOptionPane.WARNING_MESSAGE);
                Map<Integer, Object> res = EmployeeBLL.edit(newValues,Integer.parseInt((String)newValues.get("id")),oldValues);

                ResponseType.showResponse(res,managerLayout);

                managerLayout.setValueField(-1);
                managerLayout.updateTable(EmployeeBLL.all());
            }

            @Override
            public void delete(Map<String, Object> values, int selectedRow) {
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(managerLayout,"Please select edit row!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int i =JOptionPane.showConfirmDialog(managerLayout,"Are you sure you want to delete this item (id "+values.get("id")+")?","Warning",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (i == JOptionPane.YES_OPTION) {
                    Map<Integer, Object> res = EmployeeBLL.remove(values);

                    ResponseType.showResponse(res,managerLayout);

                    managerLayout.setValueField(-1);
                    managerLayout.updateTable(EmployeeBLL.all());
                }

            }

            @Override
            public void find(Map<String, Object> values) {

            }
        });
        managerLayout.updateTable(EmployeeDAL.all());
    }

    public void hide() {
        this.managerLayout.setVisible(false);
    }

    public void show() {
        this.managerLayout.setVisible(true);
    }

    public static void main(String[] args) {
        EmployeeGUI employeeGUI = new EmployeeGUI();
        employeeGUI.show();
    }
}
