package DAL;

import BLL.DTO.TimeKeeping;
import DAL.Eloquent.Eloquent;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TimeKeepingDAL {

    private static Map<String, Integer> getTypesSQL() {
        Field[] fields = TimeKeeping.class.getDeclaredFields();
        Map<String, Integer> typeSql = new HashMap<>();
        int[] t = new int[] {Types.BIGINT, Types.DATE, Types.INTEGER, Types.BIGINT, Types.TIMESTAMP, Types.TIMESTAMP };
        for (int i = 0; i < fields.length; i++) {
            typeSql.put(fields[i].getName(),t[i]);
        }
        return typeSql;
    }

    public static String[][] all(Map<String, Integer> sort) {
        Eloquent eloquent = new Eloquent();
        Vector<TimeKeeping> vector = new Vector<>();
        eloquent.all(TimeKeeping.class,sort).forEach(map -> {
            vector.add(new TimeKeeping(map));
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
        Vector<TimeKeeping> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        Field[] fields = TimeKeeping.class.getDeclaredFields();
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
        eloquent.whereOr(TimeKeeping.class,map,Eloquent.LIKE,getTypesSQL()).forEach(vector1 -> vector.add(new TimeKeeping(vector1)));
        eloquent.close();
        String[][] result = new String[vector.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = vector.get(i).toStrings();
        }
        return result;
    }

    public static String[][] get(String key, Object value, int comparison) {

        Eloquent eloquent = new Eloquent();
        Vector<TimeKeeping> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        map.put(key,value);
        eloquent.whereOr(TimeKeeping.class,map,comparison,getTypesSQL()).forEach(map1 -> {
            vector.add(new TimeKeeping(map1));
        });
        eloquent.close();
        String[][] result = new String[vector.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = vector.get(i).toStrings();
        }
        return result;
    }

    public static TimeKeeping[] getObjects(String key, Object value, int comparison) {

        Eloquent eloquent = new Eloquent();
        Vector<TimeKeeping> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        map.put(key,value);
        eloquent.whereOr(TimeKeeping.class,map,comparison,getTypesSQL()).forEach(map1 -> {
            vector.add(new TimeKeeping(map1));
        });
        eloquent.close();
        TimeKeeping[] timeKeepings = new TimeKeeping[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            timeKeepings[i] = vector.get(i);
        }
        return timeKeepings;
    }


    public static int insert(Map<String, Object> values) {
        Eloquent eloquent = new Eloquent();
        int i = -1;
        values.remove("id");
        try {
            i = eloquent.insert(new TimeKeeping(values),getTypesSQL());
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
            i = eloquent.update(TimeKeeping.class,data,id,getTypesSQL());
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
            i = eloquent.delete(TimeKeeping.class,id);
            eloquent.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}

