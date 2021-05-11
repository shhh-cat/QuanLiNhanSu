package GUI;

import BLL.DTO.Department;
import BLL.DepartmentBLL;
import BLL.ResponseType;
import GUI.ActionInterface.BackDashboard;
import GUI.ActionInterface.ManagerAction;
import GUI.Layout.ManagerLayout;
import GUI.Ultilities.FieldPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DepartmentGUI implements BackDashboard {
    private final ManagerLayout managerLayout;

    DashboardGUI dashboardGUI;

    public DepartmentGUI() throws IOException {
        String[] columns = GUI.getColumns(Department.class);
        String[] displays = GUI.getColumnsDisplay(Department.class);

        int[] types = new int[columns.length];
        String[][] typesContent = new String[columns.length][];
        for (int i = 0; i < columns.length; i++) {
            types[i] =  FieldPanel.TEXT_FIELD ;
            typesContent[i] =  null ;
        }

        managerLayout = new ManagerLayout("Department Manager",displays, columns,null, types, typesContent, new ManagerAction() {
            @Override
            public void add(Map<String, Object> values) {
                int q = JOptionPane.showConfirmDialog(managerLayout,DataModal(values)+ "Are you sure ?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (q == JOptionPane.YES_OPTION) {
                    Map<Integer, Object> res = DepartmentBLL.create(values);
                    if (ResponseType.showResponse(res,managerLayout)) {
                        managerLayout.updateTable(DepartmentBLL.all(null));
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
                    Map<Integer, Object> res = DepartmentBLL.edit(newValues,Integer.parseInt((String)newValues.get("id")),oldValues);

                    if (ResponseType.showResponse(res,managerLayout)) {
                        managerLayout.updateTable(DepartmentBLL.all(null));
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
                    Map<Integer, Object> res = DepartmentBLL.remove(values);

                    if (ResponseType.showResponse(res,managerLayout)) {
                        managerLayout.updateTable(DepartmentBLL.all(null));
                    }
                }

            }

            @Override
            public void find() {
                String column = String.valueOf(JOptionPane.showInputDialog(null,"Choose field you want to search:","Search",JOptionPane.INFORMATION_MESSAGE,null,columns,null));
                Object value = JOptionPane.showInputDialog(null,"Keywords of "+column+":","Search",JOptionPane.INFORMATION_MESSAGE);
                managerLayout.updateTable(DepartmentBLL.search(column,value));
                managerLayout.searched("Result of ["+column+"]: "+value);
            }

            @Override
            public void sort(String column, int sort) {
                Map<String, Integer> m = new HashMap<>();
                m.put(column,sort);
                managerLayout.updateTable(DepartmentBLL.all(m));
            }

            @Override
            public void showAll() {
                managerLayout.updateTable(DepartmentBLL.all(null));
            }

            @Override
            public void back() {
                managerLayout.dispose();
                dashboardGUI.show();
            }
        });
        managerLayout.updateTable(DepartmentBLL.all(null));
    }

    private String DataModal(Map<String, Object> data) {
        StringBuilder o = new StringBuilder();
        for (Map.Entry<String, Object> stringObjectEntry : data.entrySet()) {
            if (stringObjectEntry.getKey().equals("id")) continue;
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

    public void dispose() {
        this.managerLayout.dispose();
    }


    @Override
    public void setDashboard(DashboardGUI dashboardGUI) {
        this.dashboardGUI = dashboardGUI;
    }
}
