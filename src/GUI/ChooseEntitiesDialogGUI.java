package GUI;

import BLL.DepartmentBLL;
import BLL.EmployeeBLL;
import GUI.ActionInterface.BackDashboard;
import GUI.ActionInterface.TimekeepingAction;
import GUI.Layout.ChooseEntitiesDialog;
import GUI.Layout.TimekeepingLayout;

import java.io.IOException;
import java.util.Map;

public class ChooseEntitiesDialogGUI implements BackDashboard {

    private final ChooseEntitiesDialog chooseEntitiesDialog;
    DashboardGUI dashboardGUI;
    String[][] data;
    Class<?> c;

    public ChooseEntitiesDialogGUI(Class<?> c) {
        this.c = c;
        String[] columns = GUI.getColumns(c);
        String[] displays = GUI.getColumnsDisplay(c);
        this.updateData();
        chooseEntitiesDialog = new ChooseEntitiesDialog(columns,displays,null);
        chooseEntitiesDialog.updateTable(data);
    }

    private void updateData() {
        switch (c.getSimpleName()) {
            case "Employee" -> data = EmployeeBLL.all(null);
            case "Department" -> DepartmentBLL.all(null);
        }
    }

    public void hide() {
        this.chooseEntitiesDialog.setVisible(false);
    }

    public void show() {
        this.chooseEntitiesDialog.setVisible(true);
        this.updateData();
    }

    public void dispose() {
        this.chooseEntitiesDialog.dispose();
    }

    @Override
    public void setDashboard(DashboardGUI dashboardGUI) {
        this.dashboardGUI = dashboardGUI;
    }
}
