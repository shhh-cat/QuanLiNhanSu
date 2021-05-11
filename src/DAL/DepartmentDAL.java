package DAL;

import BLL.DTO.Department;
import BLL.DTO.Employee;
import DAL.Eloquent.Eloquent;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DepartmentDAL {

    private static Map<String, Integer> getTypesSQL() {
            Field[] fields = Department.class.getDeclaredFields();
        Map<String, Integer> typeSql = new HashMap<>();
        int[] t = new int[] {Types.BIGINT, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP };
        for (int i = 0; i < fields.length; i++) {
            typeSql.put(fields[i].getName(),t[i]);
        }
        return typeSql;
    }

    public static String[][] all(Map<String, Integer> sort) {
        Eloquent eloquent = new Eloquent();
        Vector<Department> vector = new Vector<>();
        eloquent.all(Department.class,sort).forEach(map -> {
            vector.add(new Department(map));
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
        Vector<Department> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        map.put(key,value);
        eloquent.whereOr(Department.class,map,comparison,getTypesSQL()).forEach(map1 -> {
            vector.add(new Department(map1));
        });
        eloquent.close();
        String[][] result = new String[vector.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = vector.get(i).toStrings();
        }
        return result;
    }

    public static Department getField(int id) {
        System.out.println(id);
        Eloquent eloquent = new Eloquent();
        Vector<Department> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        eloquent.whereAnd(Department.class,map,Eloquent.EQUAL,getTypesSQL()).forEach(map1 -> {
            vector.add(new Department(map1));
            System.out.println(map1.get("name"));
        });
        eloquent.close();
        return vector.get(0);
    }


    public static int insert(Map<String, Object> values) {
        Eloquent eloquent = new Eloquent();
        int i = -1;
        try {
            i = eloquent.insert(new Department(values,true),getTypesSQL());
            eloquent.close();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static int update(Map<String, Object> data, int id) {
        Eloquent eloquent = new Eloquent();
        int i = -1;
        try {
            i = eloquent.update(Department.class,data,id,getTypesSQL());
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
            i = eloquent.delete(Department.class,id);
            eloquent.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}

