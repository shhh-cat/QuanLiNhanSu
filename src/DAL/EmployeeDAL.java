package DAL;

import DAL.Eloquent.Eloquent;
import BLL.DTO.Employee;

import java.awt.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class EmployeeDAL {

    private static Map<String, Integer> getTypesSQL() {
        Field[] fields = Employee.class.getDeclaredFields();
        Map<String, Integer> typeSql = new HashMap<>();
        int[] t = new int[] {Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TINYINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP };
        for (int i = 0; i < fields.length; i++) {
            typeSql.put(fields[i].getName(),t[i]);
        }
        return typeSql;
    }

    public static String[][] all(Map<String, Integer> sort) {
        Eloquent eloquent = new Eloquent();
        Vector<Employee> vector = new Vector<>();
        eloquent.all(Employee.class,sort).forEach(map -> {
            vector.add(new Employee(map));
        });
        eloquent.close();
        String[][] result = new String[vector.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = vector.get(i).toStrings();
        }
        return result;
    }


    public static String[][] get(String key, Object value, int comparison) {

        Eloquent eloquent = new Eloquent();
        Vector<Employee> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        if (key.equals("gender")) {
            value = Boolean.parseBoolean(String.valueOf(value.equals("Nam")));
            map.put(key, (boolean)value);
        }
        eloquent.where(Employee.class,map,comparison,getTypesSQL()).forEach(map1 -> {
            vector.add(new Employee(map1));
        });
        eloquent.close();
        String[][] result = new String[vector.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = vector.get(i).toStrings();
        }
        return result;
    }


    public static int insert(Map<String, Object> values) {
        values.put("gender", ((String) values.get("gender")).equals("Nam"));
        Eloquent eloquent = new Eloquent();
        int i = -1;
        try {
            i = eloquent.insert(new Employee(values,true),getTypesSQL());
            eloquent.close();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int update(Map<String, Object> data, int id) {
        data.put("gender", ((String) data.get("gender")).equals("Nam"));
        Eloquent eloquent = new Eloquent();
        int i = -1;
        try {
            i = eloquent.update(Employee.class,data,id,getTypesSQL());
            eloquent.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int delete(int id) {
        Eloquent eloquent = new Eloquent();
        int i = -1;
        try {
            i = eloquent.delete(Employee.class,id);
            eloquent.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}

