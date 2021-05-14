package GUI;

import BLL.DTO.Employee;
import DAL.EmployeeDAL;
import GUI.ActionInterface.BackDashboard;
import GUI.ActionInterface.PayrollAction;
import GUI.Layout.ChooseEntitiesDialog;
import GUI.Layout.PayrollLayout;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

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
                int month;
                int year;
                try {
                    month = (int) JOptionPane.showInputDialog(payrollLayout, "Please choose MONTH: ", "Month of Payroll", JOptionPane.INFORMATION_MESSAGE, null, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, LocalDate.now().getMonthValue());
                    year = (int) JOptionPane.showInputDialog(payrollLayout, "Please choose YEAR: ", "Year of Payroll", JOptionPane.INFORMATION_MESSAGE, null, new Integer[]{2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021}, LocalDate.now().getYear());
                } catch (NullPointerException e) {
                    dashboardGUI.show();
                    return;
                }
                employee = EmployeeDAL.getField(id);
                payrollLayout.setVisible(true);
                payrollLayout.updateData(employee,month,year);
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
        int month;
        int year;
        try {
            month = (int) JOptionPane.showInputDialog(payrollLayout, "Please choose MONTH: ", "Month of Payroll", JOptionPane.INFORMATION_MESSAGE, null, new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, LocalDate.now().getMonthValue());
            year = (int) JOptionPane.showInputDialog(payrollLayout, "Please choose YEAR: ", "Year of Payroll", JOptionPane.INFORMATION_MESSAGE, null, new Integer[]{2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021}, LocalDate.now().getYear());
        } catch (NullPointerException e) {
            dashboardGUI.show();
            return;
        }
        employee = EmployeeDAL.getField(id);
        payrollLayout.setVisible(true);
        payrollLayout.updateData(employee,month,year);


    }

    public void dispose() {
        this.payrollLayout.dispose();
    }

    @Override
    public void setDashboard(DashboardGUI dashboardGUI) {
        this.dashboardGUI = dashboardGUI;
    }
}
