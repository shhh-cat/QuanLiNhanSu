package GUI;

import BLL.DTO.Department;
import BLL.DTO.Employee;
import BLL.DTO.Project;
import BLL.EmployeeBLL;
import BLL.ResponseType;
import GUI.ActionInterface.BackDashboard;
import GUI.ActionInterface.ManagerAction;
import GUI.Layout.ManagerLayout;
import GUI.Ultilities.DateLabelFormatter;
import GUI.Ultilities.FieldPanel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class EmployeeGUI implements BackDashboard {
    private final ManagerLayout managerLayout;
    DashboardGUI dashboardGUI;

    public EmployeeGUI() throws IOException {
        String[] columns = GUI.getColumns(Employee.class);
        String[] displays = GUI.getColumnsDisplay(Employee.class);
        int[] types = new int[columns.length];
        String[][] typesContent = new String[columns.length][];
        for (int i = 0; i < columns.length; i++) {
            switch (columns[i]) {
                case "gender" -> {
                    types[i] = FieldPanel.COMBO_BOX;
                    typesContent[i] = new String[]{"Nam", "Nữ"};
                }
                case "start_date" -> {
                    types[i] = FieldPanel.DATE_PICKER;
                    typesContent[i] = null;
                }
                default -> {
                    types[i] = FieldPanel.TEXT_FIELD;
                    typesContent[i] = null;
                }
            }
        }

        Map<String,Class<?>> entityFields = Map.of(
                "department_id", Department.class,
                "project_id", Project.class
        );

        managerLayout = new ManagerLayout("Employee Manager",displays,columns ,entityFields, types, typesContent, new ManagerAction() {
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
                Object value = null;
                UtilDateModel model = new UtilDateModel();
                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                JDatePanelImpl jDatePanel = new JDatePanelImpl(model,p);
                JDatePickerImpl jDatePicker = new JDatePickerImpl(jDatePanel, new DateLabelFormatter());
                Date date;
                switch (column) {
                    case "start_date":
                        if (JOptionPane.showConfirmDialog(null, jDatePicker, "start date", JOptionPane.DEFAULT_OPTION) == JOptionPane.OK_OPTION) {
                            date = (Date) jDatePicker.getModel().getValue();
                            value = date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                        }
                        break;
                    case "gender":
                        value = JOptionPane.showInputDialog(null, "Choose " + column + ":", "Search", JOptionPane.INFORMATION_MESSAGE, null,
                                new String[]{"Nam", "Nữ"}, null);
                        break;
                    default:
                        value = JOptionPane.showInputDialog(null, "Keywords of " + column + ":", "Search", JOptionPane.INFORMATION_MESSAGE);
                        break;

                }

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

            @Override
            public void back() {
                managerLayout.dispose();
                dashboardGUI.show();
            }
        });
        managerLayout.updateTable(EmployeeBLL.all(null));
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
