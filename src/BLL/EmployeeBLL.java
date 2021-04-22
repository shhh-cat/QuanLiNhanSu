package BLL;

import DAL.EmployeeDAL;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class EmployeeBLL {


    public static String[][] all() {
        return EmployeeDAL.all();
    }
    public static Map<Integer,Object> create(Map<String, Object> values) {
        Map<Integer,Object> response = new HashMap<Integer,Object>();

        //validate empty
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getKey().equals("id")) continue;
            if (String.valueOf(entry.getValue()).isBlank()) {
                response.put(ResponseType.INVALID_DATA,entry.getKey() + " is required");
                return response;
            }
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
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            if (entry.getKey().equals("id")) continue;
            if (String.valueOf(entry.getValue()).isBlank()) {
                response.put(ResponseType.INVALID_DATA,entry.getKey() + " is required");
                return response;
            }
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