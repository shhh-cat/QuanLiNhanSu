package DAL;

import BLL.DTO.Employee;
import BLL.DTO.Project;
import DAL.Eloquent.Eloquent;

import java.lang.reflect.Field;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ProjectDAL {

    private static Map<String, Integer> getTypesSQL() {
        Field[] fields = Project.class.getDeclaredFields();
        Map<String, Integer> typeSql = new HashMap<>();
        int[] t = new int[] {Types.BIGINT, Types.VARCHAR, Types.DATE, Types.TIMESTAMP, Types.TIMESTAMP };
        for (int i = 0; i < fields.length; i++) {
            typeSql.put(fields[i].getName(),t[i]);
        }
        return typeSql;
    }

    public static String[][] all(Map<String, Integer> sort) {
        Eloquent eloquent = new Eloquent();
        Vector<Project> vector = new Vector<>();
        eloquent.all(Project.class,sort).forEach(map -> {
            vector.add(new Project(map));
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
        Vector<Project> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        Field[] fields = Project.class.getDeclaredFields();
        for (Field field : fields) {
            switch (field.getType().getSimpleName()) {
                case "int":
                    try {
                        int data = Integer.parseInt(keyword);
                        map.put(field.getName(),data);
                    } catch (NumberFormatException ignored) {}
                    break;
                case "LocalDate":

                    break;

                default:
                    map.put(field.getName(),keyword);
                    break;
            }
        }
        eloquent.whereOr(Project.class,map,Eloquent.LIKE,getTypesSQL()).forEach(vector1 -> vector.add(new Project(vector1)));
        eloquent.close();
        String[][] result = new String[vector.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = vector.get(i).toStrings();
        }
        return result;
    }

    public static String[][] get(String key, Object value, int comparison) {

        Eloquent eloquent = new Eloquent();
        Vector<Project> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        map.put(key,value);
        eloquent.whereOr(Project.class,map,comparison,getTypesSQL()).forEach(map1 -> {
            vector.add(new Project(map1));
        });
        eloquent.close();
        String[][] result = new String[vector.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = vector.get(i).toStrings();
        }
        return result;
    }

    public static Project getField(int id) {

        Eloquent eloquent = new Eloquent();
        Vector<Project> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        eloquent.whereAnd(Project.class,map,Eloquent.EQUAL,getTypesSQL()).forEach(map1 -> {
            vector.add(new Project(map1));
        });
        eloquent.close();
        return vector.get(0);
    }


    public static int insert(Map<String, Object> values) {
        Eloquent eloquent = new Eloquent();
        int i = -1;
        values.remove("id");
        try {
            i = eloquent.insert(new Project(values),getTypesSQL());
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
            i = eloquent.update(Project.class,data,id,getTypesSQL());
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
            i = eloquent.delete(Project.class,id);
            eloquent.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}

