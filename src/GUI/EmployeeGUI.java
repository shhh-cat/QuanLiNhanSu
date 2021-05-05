package GUI;

import BLL.EmployeeBLL;
import BLL.ResponseType;
import GUI.ActionInterface.ManagerAction;
import GUI.Layout.ManagerLayout;
import GUI.Ultilities.FieldPanel;
import com.github.javafaker.Faker;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class EmployeeGUI {
    private final ManagerLayout managerLayout;

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

        managerLayout = new ManagerLayout("Employee Manager", columns, types, typesContent, new ManagerAction() {
            @Override
            public void add(Map<String, Object> values) {
                int q = JOptionPane.showConfirmDialog(managerLayout,DataModal(values)+ "Are you sure ?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (q == JOptionPane.YES_OPTION) {
                    Map<Integer, Object> res = EmployeeBLL.create(values);
                    if (ResponseType.showResponse(res,managerLayout)) {
                        managerLayout.updateTable(EmployeeBLL.all(null));
                    }
                }

            }

            @Override
            public void update(Map<String, Object> newValues, int selectedRow, Map<String, Object> oldValues) {
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(managerLayout,"Please select edit row!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int q = JOptionPane.showConfirmDialog(managerLayout,DataModal(newValues)+ "Are you sure ?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (q == JOptionPane.YES_OPTION) {
                    Map<Integer, Object> res = EmployeeBLL.edit(newValues,Integer.parseInt((String)newValues.get("id")),oldValues);

                    if (ResponseType.showResponse(res,managerLayout)) {
                        managerLayout.updateTable(EmployeeBLL.all(null));
                    }
                }

            }

            @Override
            public void delete(Map<String, Object> values, int selectedRow) {
                if (selectedRow < 0) {
                    JOptionPane.showMessageDialog(managerLayout,"Please select edit row!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int i =JOptionPane.showConfirmDialog(managerLayout, "Are you sure you want to delete this item ?\n" + DataModal(values),"Warning",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (i == JOptionPane.YES_OPTION) {
                    Map<Integer, Object> res = EmployeeBLL.remove(values);

                    if (ResponseType.showResponse(res,managerLayout)) {
                        managerLayout.updateTable(EmployeeBLL.all(null));
                    }
                }

            }

            @Override
            public void find() {
                String column = String.valueOf(JOptionPane.showInputDialog(null,"Choose field you want to search:","Search",JOptionPane.INFORMATION_MESSAGE,null,columns,null));
                Object value = (column.equals("gender"))
                    ? JOptionPane.showInputDialog(null,"Choose " + column + ":","Search", JOptionPane.INFORMATION_MESSAGE, null, new String[] {"Nam", "Nữ"},null)
                    : JOptionPane.showInputDialog(null,"Keywords of "+column+":","Search",JOptionPane.INFORMATION_MESSAGE);
                managerLayout.updateTable(EmployeeBLL.search(column,value));
                managerLayout.searched("Result of ["+column+"]: "+value);
            }

            @Override
            public void sort(String column, int sort) {
                Map<String, Integer> m = new HashMap<>();
                m.put(column,sort);
                managerLayout.updateTable(EmployeeBLL.all(m));
            }

            @Override
            public void showAll() {
                managerLayout.updateTable(EmployeeBLL.all(null));
            }
        });
        managerLayout.updateTable(EmployeeBLL.all(null));
    }

    private String DataModal(Map<String, Object> data) {
        StringBuilder o = new StringBuilder();
        for (Map.Entry<String, Object> stringObjectEntry : data.entrySet()) {
            o.append(stringObjectEntry.getKey());
            o.append(": ").append(stringObjectEntry.getValue()).append("\n");
        }
        return o.toString();
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
