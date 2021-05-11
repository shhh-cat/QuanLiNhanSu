package DAL;

import BLL.DTO.Employee;
import BLL.DTO.User;
import DAL.Eloquent.Eloquent;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class UserDAL {
    private static Map<String, Integer> getTypesSQL() {
        Field[] fields = User.class.getDeclaredFields();
        Map<String, Integer> typeSql = new HashMap<>();
        int[] t = new int[] {Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP };
        for (int i = 0; i < fields.length; i++) {
            typeSql.put(fields[i].getName(),t[i]);
        }
        return typeSql;
    }

    public static String[][] get(String key, Object value, int comparison) {
        Eloquent eloquent = new Eloquent();
        Vector<User> vector = new Vector<>();
        Map<String, Object> map = new HashMap<>();
        map.put(key,value);
        eloquent.whereAnd(User.class,map,comparison,getTypesSQL()).forEach(map1 -> {
            vector.add(new User(map1));
        });
        eloquent.close();
        String[][] result = new String[vector.size()][];
        for (int i = 0; i < result.length; i++) {
            result[i] = vector.get(i).toStrings();
        }
        return result;
    }
}
