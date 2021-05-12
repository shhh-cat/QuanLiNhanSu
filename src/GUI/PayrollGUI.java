package GUI;

import BLL.DTO.Employee;
import DAL.EmployeeDAL;
import GUI.ActionInterface.BackDashboard;
import GUI.ActionInterface.PayrollAction;
import GUI.Layout.ChooseEntitiesDialog;
import GUI.Layout.PayrollLayout;

import javax.swing.*;

public class PayrollGUI implements BackDashboard {
    private final PayrollLayout payrollLayout;
    DashboardGUI dashboardGUI;
    Employee employee;
    public PayrollGUI() {
        payrollLayout = new PayrollLayout(new PayrollAction() {
            @Override
            public void back() {
                payrollLayout.dispose();
                dashboardGUI.show();
            }

            @Override
            public void another() {
                if (EmployeeDAL.all(null).length == 0) {
                    JOptionPane.showMessageDialog(null,"No any employee");
                    return;
                }
                payrollLayout.dispose();
                int id = Integer.parseInt(payrollLayout.getIDEmployee());
                employee = EmployeeDAL.getField(id);
                payrollLayout.setVisible(true);
                payrollLayout.updateData(employee);
            }
        });
    }

    public void hide() {
        this.payrollLayout.setVisible(false);
    }

    public void show() {
        if (EmployeeDAL.all(null).length == 0) {
            JOptionPane.showMessageDialog(null,"No any employee");
            return;
        }
        dashboardGUI.dispose();
        int id = Integer.parseInt(this.payrollLayout.getIDEmployee());
        employee = EmployeeDAL.getField(id);
        this.payrollLayout.setVisible(true);
        this.payrollLayout.updateData(employee);


    }

    public void dispose() {
        this.payrollLayout.dispose();
    }

    @Override
    public void setDashboard(DashboardGUI dashboardGUI) {
        this.dashboardGUI = dashboardGUI;
    }
}
