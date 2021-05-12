package GUI;

import GUI.ActionInterface.DashboardAction;
import GUI.Layout.DashboardLayout;

import javax.swing.*;
import java.io.IOException;

public class DashboardGUI {
    DashboardLayout dashboardLayout;


    public void hide() {
        this.dashboardLayout.setVisible(false);
    }

    public void show() {
        this.dashboardLayout.setVisible(true);
    }

    public void dispose() {
        dashboardLayout.dispose();
    }

    public DashboardGUI(EmployeeGUI employeeGUI, DepartmentGUI departmentGUI, TimeKeepingGUI timeKeepingGUI, ProjectGUI projectGUI, PayrollGUI payrollGUI) throws IOException {
        dashboardLayout = new DashboardLayout(new DashboardAction() {
            @Override
            public void openEmployee() {
                employeeGUI.show();
                dashboardLayout.dispose();
            }

            @Override
            public void openDepartment() {
                departmentGUI.show();
                dashboardLayout.dispose();
            }

            @Override
            public void openPayroll() {
                payrollGUI.show();
                departmentGUI.dispose();
            }

            @Override
            public void openProject() {
                projectGUI.show();
                dashboardLayout.dispose();
            }

            @Override
            public void openTimekeeping() {
                timeKeepingGUI.show();
                dashboardLayout.dispose();
            }

            @Override
            public void openExit() {
                int i = JOptionPane.showConfirmDialog(dashboardLayout,"Are you sure you want to exit ?","Warning",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if (i == JOptionPane.YES_OPTION) {
                    employeeGUI.dispose();
                    departmentGUI.dispose();
                    dashboardLayout.dispose();
                }
            }
        });
    }
}
