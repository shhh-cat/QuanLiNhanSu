package GUI;

import BLL.DTO.Employee;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.Map;

public class GUI {
    static final Map<String,String> employee = Map.of(
        "department_id","department",
        "bonus_position","bonus position (%)",
        "project_id", "project",
        "start_date", "start date"
    );

    static final Map<String,String> department = Map.of(
            "min_salary","min salary",
            "max_salary","max salary"
    );

    static final Map<String,String> project = Map.of(
        "start_date", "start date"
    );

    static final Map<String,String> timekeeping = Map.of(
            "employee_id","employee",
            "violation", "violation (% minus)"
    );

    public static String[] getColumns(Class<?> c) {
        Field[] fields = c.getDeclaredFields();
        String[] columns = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columns[i] = fields[i].getName();
        }
        return columns;
    }

    public static String[] getColumnsDisplay(Class<?> c) {
        String[] columns = getColumns(c);
        String[] displays = new String[columns.length];
        Map<String,String> edit = null;
        switch (c.getSimpleName()) {
            case "Employee":
                edit = employee;
                break;
            case "Department":
                edit = department;
                break;
            case "TimeKeeping":
                edit = timekeeping;
                break;
            default:
                return columns;
        }
        for (int i = 0; i < columns.length; i++) {
            displays[i] = columns[i];
            for (Map.Entry<String, String> stringStringEntry : edit.entrySet()) {
                if (displays[i].equals(stringStringEntry.getKey())) {
                    displays[i] = stringStringEntry.getValue();
                    break;
                }
            }
        }
        return displays;
    }
}
