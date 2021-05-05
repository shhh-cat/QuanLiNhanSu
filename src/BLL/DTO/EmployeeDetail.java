package BLL.DTO;

import java.util.Map;

public class EmployeeDetail {
    private int id;
    private int employee_id;
    private int department_position_id;
    private int salary;
    private int project_id;
    private int month_start;
    private int year_start;

    public EmployeeDetail(int employee_id, int department_position_id, int salary, int project_id, int month_start, int year_start) {
        this.employee_id = employee_id;
        this.department_position_id = department_position_id;
        this.salary = salary;
        this.project_id = project_id;
        this.month_start = month_start;
        this.year_start = year_start;
    }

    public EmployeeDetail(Map<String, Object> map) {
        this.id = (int)map.get("id");
        this.employee_id = (int)map.get("id");
        this.department_position_id = (int)map.get("id");
        this.salary = (int)map.get("id");
        this.project_id = (int)map.get("id");
        this.month_start = (int)map.get("id");
        this.year_start = (int)map.get("id");
    }

    public EmployeeDetail(Map<String, Object> map, boolean noId) {
        this.employee_id = (int)map.get("id");
        this.department_position_id = (int)map.get("id");
        this.salary = (int)map.get("id");
        this.project_id = (int)map.get("id");
        this.month_start = (int)map.get("id");
        this.year_start = (int)map.get("id");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getDepartment_position_id() {
        return department_position_id;
    }

    public void setDepartment_position_id(int department_position_id) {
        this.department_position_id = department_position_id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getMonth_start() {
        return month_start;
    }

    public void setMonth_start(int month_start) {
        this.month_start = month_start;
    }

    public int getYear_start() {
        return year_start;
    }

    public void setYear_start(int year_start) {
        this.year_start = year_start;
    }

    public String[] toStrings() {
        return new String[] {
                String.valueOf(id),
                String.valueOf(employee_id),
                String.valueOf(department_position_id),
                String.valueOf(salary),
                String.valueOf(project_id),
                String.valueOf(month_start),
                String.valueOf(year_start),
        };
    }
}
