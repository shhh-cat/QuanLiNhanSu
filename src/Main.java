import BLL.DTO.Employee;
import BLL.DTO.Project;
import GUI.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        EmployeeGUI employeeGUI = new EmployeeGUI();
        DepartmentGUI departmentGUI = new DepartmentGUI();
        TimeKeepingGUI timeKeepingGUI = new TimeKeepingGUI();
        ProjectGUI projectGUI = new ProjectGUI();
        DashboardGUI dashboardGUI = new DashboardGUI(employeeGUI, departmentGUI, timeKeepingGUI,projectGUI);
        employeeGUI.setDashboard(dashboardGUI);
        departmentGUI.setDashboard(dashboardGUI);
        timeKeepingGUI.setDashboard(dashboardGUI);
        projectGUI.setDashboard(dashboardGUI);
        LoginGUI loginGUI = new LoginGUI(dashboardGUI);
        loginGUI.show();
    }
}
