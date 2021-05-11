package DAL;

import BLL.DTO.EmployeeDetail;
import DAL.Eloquent.Eloquent;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class EmployeeDetailDAL {

    private static Map<String, Integer> getTypesSQL() {
        Field[] fields = EmployeeDetail.class.getDeclaredFields();
        Map<String, Integer> typeSql = new HashMap<>();
        int[] t = new int[] {Types.BIGINT, Types.BIGINT, Types.BIGINT, Types.INTEGER, Types.BIGINT, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP };
        for (int i = 0; i < fields.length; i++) {
            typeSql.put(fields[i].getName(),t[i]);
        }
        return typeSql;
    }

    public static String[][] all(Map<String, Integer> sort) {
        Eloquent eloquent = new Eloquent();
        Vector<EmployeeDetail> vector = new Vector<>();
        eloquent.all(EmployeeDetail.class,sort).forEach(map -> {
            vector.add(new EmployeeDetail(map));
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
        Vector<EmployeeDetail> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        map.put(key,value);
        eloquent.whereOr(EmployeeDetail.class,map,comparison,getTypesSQL()).forEach(map1 -> {
            vector.add(new EmployeeDetail(map1));
        });
        eloquent.close();
        String[][] result = new String[vector.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = vector.get(i).toStrings();
        }
        return result;
    }


    public static int insert(Map<String, Object> values) {
        Eloquent eloquent = new Eloquent();
        int i = -1;
        try {
            i = eloquent.insert(new EmployeeDetail(values,true),getTypesSQL());
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
            i = eloquent.update(EmployeeDetail.class,data,id,getTypesSQL());
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
            i = eloquent.delete(EmployeeDetail.class,id);
            eloquent.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}

