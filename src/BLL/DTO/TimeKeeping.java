package BLL.DTO;

import DAL.EmployeeDAL;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

public class TimeKeeping {
    private int id;
    private LocalDate date;
    private int violation;
    private int employee_id;

    public TimeKeeping(int id, LocalDate date, int violation, int employee_id) {
        this.id = id;
        this.date = date;
        this.violation = violation;
        this.employee_id = employee_id;
    }

    public TimeKeeping(int id, LocalDate date, int employee_id) {
        this.id = id;
        this.date = date;
        this.employee_id = employee_id;
    }

    public TimeKeeping(Map<String, Object> map) {
        if (map.containsKey("id"))
            this.id = (int)(map.get("id"));
        if (map.containsKey("violation")) this.violation = Integer.parseInt(String.valueOf(map.get("violation")));
        this.date = (LocalDate) map.get("date");
        this.employee_id = Integer.parseInt(String.valueOf(map.get("employee_id")));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getViolation() {
        return violation;
    }

    public void setViolation(int violation) {
        this.violation = violation;
    }

    public int getEmployee_id() {
        return employee_id;
    }




    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String[] toStrings() {
        return new String[] {
                String.valueOf(id),
                date.toString(),
                String.valueOf(violation),
                String.valueOf(employee_id)
        };
    }
}
