package BLL;

import BLL.DTO.Department;
import DAL.DepartmentDAL;
import DAL.Eloquent.Eloquent;
import DAL.EmployeeDAL;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class EmployeeBLL {


    public static String[][] all(Map<String, Integer> sort) {
        return EmployeeDAL.all(sort);
    }
    public static String[][] search(String key, Object value) {
        if (key.equals("id") || key.equals("gender") || key.equals("start_date"))
            return EmployeeDAL.get(key,value, Eloquent.EQUAL);
        return EmployeeDAL.get(key,value, Eloquent.LIKE);
    }

    private static String emptyValidate(Map<String, Object> values) {
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getKey().equals("id")) continue;
            if (String.valueOf(entry.getValue()).isBlank() || entry.getValue() == null) {
                return entry.getKey() + " is required";
            }
        }
        return null;
    }

    private static String isNumber(Map<String, Object> values) {
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getKey().equals("id")) continue;
            switch (entry.getKey()) {
                case "phonenumber":
                case "idnumber":
                case "salary":
                    if (!ResponseType.isNumeric(String.valueOf(entry.getValue())))
                        return entry.getKey()+ " must be number";
                    break;
                case "bonus_position":
                    if (!ResponseType.isNumeric(String.valueOf(entry.getValue())))
                        return entry.getKey()+ " must be number";
                    if (Integer.parseInt(String.valueOf(entry.getValue())) > 100 || Integer.parseInt(String.valueOf(entry.getValue())) < 0)
                        return entry.getKey()+ " should be between 0 and 100";
                    break;
            }
        }
        return null;
    }


    public static Map<Integer,Object> create(Map<String, Object> values) {
        Map<Integer,Object> response = new HashMap<Integer,Object>();

        //validate empty
        String emptyValue = emptyValidate(values);
        if (emptyValue != null) {
            response.put(ResponseType.INVALID_DATA,emptyValue + " is required");
            return response;
        }

        //is Number
        String isNum = isNumber(values);
        if (isNum != null) {
            response.put(ResponseType.INVALID_DATA,isNum);
            return response;
        }


        // check salary
        int salary = Integer.parseInt(String.valueOf(values.get("salary")));
        Department department = DepartmentDAL.getField(Integer.parseInt(String.valueOf(values.get("department_id"))));
        int min_salary = department.getMin_salary();
        int max_salary = department.getMax_salary();
        if (salary < min_salary || salary > max_salary) {
            response.put(ResponseType.INVALID_DATA,"Invalid salary, "+department.getName()+" department salary from "+min_salary+" to "+max_salary);
            return response;
        }

        int i = EmployeeDAL.insert(values);
        switch (i) {
            case -1 -> response.put(ResponseType.FAILED, "Failed");
            case -2 -> response.put(ResponseType.ERROR_SQL, "SQL ERROR");
            default -> response.put(ResponseType.SUCCESS, "Create successfully");
        }
        return response;
    }
    public static Map<Integer,Object> edit(Map<String, Object> data, int id,Map<String, Object> old) {
        Map<Integer,Object> response = new HashMap<Integer,Object>();

        //validate empty
        String emptyValue = emptyValidate(data);
        if (emptyValue != null) {
            response.put(ResponseType.INVALID_DATA,emptyValue + " is required");
            return response;
        }

        //is Number
        String isNum = isNumber(data);
        if (isNum != null) {
            response.put(ResponseType.INVALID_DATA,isNum);
            return response;
        }

        if (data.equals(old)) {
            response.put(ResponseType.INVALID_DATA,"Nothing changes");
            return response;
        }

        int i = EmployeeDAL.update(data,id);
        switch (i) {
            case -1 -> response.put(ResponseType.FAILED, "Failed");
            case -2 -> response.put(ResponseType.ERROR_SQL, "SQL ERROR");
            default -> response.put(ResponseType.SUCCESS, "Edit successfully");
        }

        return response;
    }
    public static Map<Integer,Object> remove(Map<String, Object> data) {
        Map<Integer,Object> response = new HashMap<Integer,Object>();

        int i = EmployeeDAL.delete(Integer.parseInt(String.valueOf(data.get("id"))));
        switch (i) {
            case -1 -> response.put(ResponseType.FAILED, "Failed");
            case -2 -> response.put(ResponseType.ERROR_SQL, "SQL ERROR");
            default -> response.put(ResponseType.SUCCESS, "Remove successfully");
        }
        return response;
    }
}
