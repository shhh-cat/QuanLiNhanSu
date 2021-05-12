package BLL;

import DAL.Eloquent.Eloquent;
import DAL.TimeKeepingDAL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TimeKeepingBLL {


    public static String[][] all(Map<String, Integer> sort) {
        return TimeKeepingDAL.all(sort);
    }
    public static String[][] search(String key, Object value) {
        if (key.equals("id") || key.equals("date"))
            return TimeKeepingDAL.get(key,value, Eloquent.EQUAL);
        return TimeKeepingDAL.get(key,value, Eloquent.LIKE);
    }

    private static String isNumber(Map<String, Object> values) {
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getKey().equals("id")) continue;
            if ("violation".equals(entry.getKey())) {
                if (!ResponseType.isNumeric(String.valueOf(entry.getValue())))
                    return entry.getKey() + " must be number";
                if (Integer.parseInt(String.valueOf(entry.getValue())) > 100 || Integer.parseInt(String.valueOf(entry.getValue())) < 0)
                    return entry.getKey() + " should be between 0 and 100";
            }
        }
        return null;
    }

    public static Map<Integer,Object> create(Map<String, Object> values) {
        Map<Integer,Object> response = new HashMap<Integer,Object>();

        //validate empty
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getKey().equals("id")) continue;
            if (String.valueOf(entry.getValue()).isBlank() || entry.getValue() == null) {
                response.put(ResponseType.INVALID_DATA,entry.getKey() + " is required");
                return response;
            }
        }

        //is Number
        String isNum = isNumber( values);
        if (isNum != null) {
            response.put(ResponseType.INVALID_DATA,isNum);
            return response;
        }

        //check duplicate
        String[][] dates = TimeKeepingDAL.get("employee_id",values.get("employee_id"),Eloquent.EQUAL);
        for (String[] date : dates) {
            if (date[1].equals(String.valueOf(values.get("date")))) {
                response.put(ResponseType.INVALID_DATA,"Employee has id: "+values.get("id")+ " attended on "+values.get("date"));
                return response;
            }
        }

        int i = TimeKeepingDAL.insert(values);
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
            if (String.valueOf(entry.getValue()).isBlank() || entry.getValue() == null) {
                response.put(ResponseType.INVALID_DATA,entry.getKey() + " is required");
                return response;
            }
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

        int i = TimeKeepingDAL.update(data,id);
        switch (i) {
            case -1 -> response.put(ResponseType.FAILED, "Failed");
            case -2 -> response.put(ResponseType.ERROR_SQL, "SQL ERROR");
            default -> response.put(ResponseType.SUCCESS, "Edit successfully");
        }

        return response;
    }
    public static Map<Integer,Object> remove(Map<String, Object> data) {
        Map<Integer,Object> response = new HashMap<Integer,Object>();

        int i = TimeKeepingDAL.delete(Integer.parseInt(String.valueOf(data.get("id"))));
        switch (i) {
            case -1 -> response.put(ResponseType.FAILED, "Failed");
            case -2 -> response.put(ResponseType.ERROR_SQL, "SQL ERROR");
            default -> response.put(ResponseType.SUCCESS, "Remove successfully");
        }
        return response;
    }
}
