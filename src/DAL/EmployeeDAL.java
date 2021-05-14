package DAL;

import DAL.Eloquent.Eloquent;
import BLL.DTO.Employee;

import javax.swing.*;
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
        int[] t = new int[] {Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TINYINT, Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.BIGINT, Types.DATE, Types.TIMESTAMP, Types.TIMESTAMP };
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

    public static String[][] relatedTo(String keyword) {
        Eloquent eloquent = new Eloquent();
        Vector<Employee> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        Field[] fields = Employee.class.getDeclaredFields();
        for (Field field : fields) {
            switch (field.getType().getSimpleName()) {
                case "int":
                    try {
                        int data = Integer.parseInt(keyword);
                        map.put(field.getName(),data);
                    } catch (NumberFormatException ignored) {}
                    break;
                case "boolean":
                    if ("nam".contains(keyword))
                        map.put(field.getName(),true);
                    if ("nữ".contains(keyword)||"nu".contains(keyword))
                        map.put(field.getName(),false);
                    break;
                default:
                    map.put(field.getName(),keyword);
                    break;
            }
        }
        eloquent.whereOr(Employee.class,map,Eloquent.LIKE,getTypesSQL()).forEach(vector1 -> vector.add(new Employee(vector1)));
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
        else map.put(key,value);
        eloquent.whereOr(Employee.class,map,comparison,getTypesSQL()).forEach(map1 -> {
            vector.add(new Employee(map1));
        });
        eloquent.close();
        String[][] result = new String[vector.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = vector.get(i).toStrings();
        }
        return result;
    }



    public static Employee getField(int id) {

        Eloquent eloquent = new Eloquent();
        Vector<Employee> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        eloquent.whereAnd(Employee.class,map,Eloquent.EQUAL,getTypesSQL()).forEach(map1 -> {
            vector.add(new Employee(map1));
        });
        eloquent.close();
        return vector.get(0);
    }


    public static int insert(Map<String, Object> values) {
        values.put("gender", ((String) values.get("gender")).equals("Nam"));
        Eloquent eloquent = new Eloquent();
        int i = -1;
        values.remove("id");
        try {
            i = eloquent.insert(new Employee(values),getTypesSQL());
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

