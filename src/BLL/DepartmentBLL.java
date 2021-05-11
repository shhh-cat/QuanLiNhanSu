package BLL;

import DAL.DepartmentDAL;
import DAL.Eloquent.Eloquent;

import java.util.HashMap;
import java.util.Map;

public class DepartmentBLL {


    public static String[][] all(Map<String, Integer> sort) {
        return DepartmentDAL.all(sort);
    }
    public static String[][] search(String key, Object value) {
        if (key.equals("id"))
            return DepartmentDAL.get(key,value, Eloquent.EQUAL);
        return DepartmentDAL.get(key,value, Eloquent.LIKE);
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
                case "min_salary", "max_salary" -> {
                    if (!ResponseType.isNumeric(String.valueOf(entry.getValue())))
                        return entry.getKey() + " must be number.";
                    if (Long.parseLong(String.valueOf(entry.getValue())) > 2000000000)
                        return entry.getKey() + " must be less than 2 billion.";
                }
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

        if(Integer.parseInt(String.valueOf(values.get("min_salary"))) >= Integer.parseInt(String.valueOf(values.get("max_salary")))) {
            response.put(ResponseType.INVALID_DATA,"Min salary must be less than Max salary");
            return response;
        }


        int i = DepartmentDAL.insert(values);
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

        if(Integer.parseInt(String.valueOf(data.get("min_salary"))) >= Integer.parseInt(String.valueOf(data.get("max_salary")))) {
            response.put(ResponseType.INVALID_DATA,"Min salary must be less than Max salary");
            return response;
        }


        if (data.equals(old)) {
            response.put(ResponseType.INVALID_DATA,"Nothing changes");
            return response;
        }

        int i = DepartmentDAL.update(data,id);
        switch (i) {
            case -1 -> response.put(ResponseType.FAILED, "Failed");
            case -2 -> response.put(ResponseType.ERROR_SQL, "SQL ERROR");
            default -> response.put(ResponseType.SUCCESS, "Edit successfully");
        }

        return response;
    }
    public static Map<Integer,Object> remove(Map<String, Object> data) {
        Map<Integer,Object> response = new HashMap<Integer,Object>();

        int i = DepartmentDAL.delete(Integer.parseInt(String.valueOf(data.get("id"))));
        switch (i) {
            case -1 -> response.put(ResponseType.FAILED, "Failed");
            case -2 -> response.put(ResponseType.ERROR_SQL, "SQL ERROR");
            default -> response.put(ResponseType.SUCCESS, "Remove successfully");
        }
        return response;
    }
}
