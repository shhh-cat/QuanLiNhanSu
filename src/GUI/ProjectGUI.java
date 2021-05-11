package GUI;

import BLL.DTO.Project;
import BLL.ProjectBLL;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ProjectGUI implements BackDashboard {
    private final ManagerLayout managerLayout;
    DashboardGUI dashboardGUI;

    public ProjectGUI() throws IOException {
        String[] columns = GUI.getColumns(Project.class);
        String[] displays = GUI.getColumnsDisplay(Project.class);
        int[] types = new int[columns.length];
        String[][] typesContent = new String[columns.length][];
        for (int i = 0; i < columns.length; i++) {
            if ("start_date".equals(columns[i])) {
                types[i] = FieldPanel.DATE_PICKER;
            } else {
                types[i] = FieldPanel.TEXT_FIELD;
            }
            typesContent[i] = null;
        }


        managerLayout = new ManagerLayout("Project Manager",displays,columns ,null, types, typesContent, new ManagerAction() {
            @Override
            public void add(Map<String, Object> values) {
                int q = JOptionPane.showConfirmDialog(managerLayout,DataModal(values)+ "Are you sure ?","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (q == JOptionPane.YES_OPTION) {
                    Map<Integer, Object> res = ProjectBLL.create(values);
                    if (ResponseType.showResponse(res,managerLayout)) {
                        managerLayout.updateTable(ProjectBLL.all(null));
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
                    Map<Integer, Object> res = ProjectBLL.edit(newValues,Integer.parseInt((String)newValues.get("id")),oldValues);

                    if (ResponseType.showResponse(res,managerLayout)) {
                        managerLayout.updateTable(ProjectBLL.all(null));
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
                    Map<Integer, Object> res = ProjectBLL.remove(values);

                    if (ResponseType.showResponse(res,managerLayout)) {
                        managerLayout.updateTable(ProjectBLL.all(null));
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
                if (column.equals("start_date")) {
                    if (JOptionPane.showConfirmDialog(null,jDatePicker,"start date", JOptionPane.DEFAULT_OPTION) == JOptionPane.OK_OPTION) {
                        date = (Date) jDatePicker.getModel().getValue();
                        value = date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                    }
                }
                else {
                        value = JOptionPane.showInputDialog(null,"Keywords of "+column+":","Search",JOptionPane.INFORMATION_MESSAGE);
                }

                managerLayout.updateTable(ProjectBLL.search(column,value));
                managerLayout.searched("Result of ["+column+"]: "+value);
            }

            @Override
            public void sort(String column, int sort) {
                Map<String, Integer> m = new HashMap<>();
                m.put(column,sort);
                managerLayout.updateTable(ProjectBLL.all(m));
            }

            @Override
            public void showAll() {
                managerLayout.updateTable(ProjectBLL.all(null));
            }

            @Override
            public void back() {
                managerLayout.dispose();
                dashboardGUI.show();
            }
        });
        managerLayout.updateTable(ProjectBLL.all(null));
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
