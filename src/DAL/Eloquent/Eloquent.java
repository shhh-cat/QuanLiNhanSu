package DAL.Eloquent;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class Eloquent extends Connector {

    public static final int EQUAL = 1;
    public static final int LIKE = 2;
    public static final int ASC = 1;
    public static final int DESC = -1;

    public Eloquent() {
        super();
    }

    public Vector<Map<String, Object>> all(Class<?> c, Map<String,Integer> sort) {
        ResultSet rs;
        Statement stmt = null;
        Vector<Map<String, Object>> vector = new Vector<>();
        try {
            stmt = getStatement();
            StringBuilder sql = new StringBuilder("SELECT * FROM " + c.getSimpleName().toLowerCase());
            if (sort != null) {
                sql.append(" ORDER BY ");
                int l = sort.entrySet().size();
                for (Map.Entry<String, Integer> stringIntegerEntry : sort.entrySet()) {
                    sql.append(stringIntegerEntry.getKey()).append(" ").append((stringIntegerEntry.getValue() == ASC) ? "ASC" : "DESC");
                    if (--l != 0)
                        sql.append(", ");
                }
            }

            rs = stmt.executeQuery(sql.toString());
            Field[] fields = c.getDeclaredFields();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (Field field : fields) {
                    map.put(field.getName(), rs.getObject(field.getName(),field.getType()));
                }
                vector.add(map);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return vector;
    }

    public Vector<Map<String, Object>> whereAnd(Class<?> c, Map<String, Object> o,int comparison,Map<String , Integer> typesSQL) {
        ResultSet rs;
        PreparedStatement pstmt = null;
        Vector<Map<String, Object>> vector = new Vector<>();
        try {
            String condition = " WHERE ";
            for (Map.Entry<String, Object> entry: o.entrySet()){
                switch (comparison) {
                    case Eloquent.EQUAL:
                        condition = condition.concat(entry.getKey() + " = ? AND ");
                        break;
                    case Eloquent.LIKE:
                        condition = condition.concat(entry.getKey() + " LIKE ? AND ");
                        break;
                    default:
                        break;
                }

            }
            condition = condition.substring(0,condition.length()-5);

            pstmt = getPreparedStatement("SELECT * FROM "+ c.getSimpleName().toLowerCase() + condition);
            System.out.println("SELECT * FROM "+ c.getSimpleName().toLowerCase() + condition);
            int i = 1;
            for (Map.Entry<String, Object> entry: o.entrySet()){
                switch (comparison) {
                    case Eloquent.EQUAL:
                        pstmt.setObject(i,entry.getValue(),typesSQL.get(entry.getKey()));
                        break;
                    case Eloquent.LIKE:
                        pstmt.setObject(i,"%" + entry.getValue() + "%",typesSQL.get(entry.getKey()));
                        break;
                    default:
                        break;
                }

            }

            rs = pstmt.executeQuery();
            Field[] fields = c.getDeclaredFields();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (Field field : fields) {
                    map.put(field.getName(), rs.getObject(field.getName(),field.getType()));
                }
                vector.add(map);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return vector;
    }

    public Vector<Map<String, Object>> whereOr(Class<?> c, Map<String, Object> o,int comparison,Map<String , Integer> typesSQL) {
        ResultSet rs;
        PreparedStatement pstmt = null;
        Vector<Map<String, Object>> vector = new Vector<>();
        try {
            String condition = " WHERE ";
            for (Map.Entry<String, Object> entry: o.entrySet()){
                switch (comparison) {
                    case Eloquent.EQUAL:
                        condition = condition.concat(entry.getKey() + " = ? OR ");
                        break;
                    case Eloquent.LIKE:
                        condition = condition.concat(entry.getKey() + " LIKE ? OR ");
                        break;
                    default:
                        break;
                }

            }
            condition = condition.substring(0,condition.length()-4);

            pstmt = getPreparedStatement("SELECT * FROM "+ c.getSimpleName().toLowerCase() + condition);
            System.out.println("SELECT * FROM "+ c.getSimpleName().toLowerCase() + condition);
            int i = 1;
            for (Map.Entry<String, Object> entry: o.entrySet()){
                switch (comparison) {
                    case Eloquent.EQUAL:
                        pstmt.setObject(i,entry.getValue(),typesSQL.get(entry.getKey()));
                        break;
                    case Eloquent.LIKE:
                        pstmt.setObject(i,"%" + entry.getValue() + "%",typesSQL.get(entry.getKey()));
                        break;
                    default:
                        break;
                }

            }

            rs = pstmt.executeQuery();
            Field[] fields = c.getDeclaredFields();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (Field field : fields) {
                    map.put(field.getName(), rs.getObject(field.getName(),field.getType()));
                }
                vector.add(map);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return vector;
    }

    public int insert(Object o,Map<String, Integer> typesSQL) throws NoSuchFieldException {
        Class<?> cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        System.out.println(fields.length);
        PreparedStatement preparedStatement = null;
        String keys = "", values = "";
        for (Field field : fields) {
            keys = keys.concat(field.getName() + ",");
            values = values.concat(((field.getName().equals("id")) ? "null" : "?") + ",");
        }

        String sql = "INSERT INTO "+cls.getSimpleName().toLowerCase()+" ("+keys+"created_at,updated_at) VALUES ("+values+"?,?)";

        int id = -1;
        ResultSet rs = null;
        try {
           preparedStatement = super.getPreparedStatement(sql,Statement.RETURN_GENERATED_KEYS);

            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                preparedStatement.setObject(i,fields[i].get(o),typesSQL.get(fields[i].getName()));
                fields[i].setAccessible(false);
            }
            preparedStatement.setTimestamp(fields.length,Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setTimestamp(fields.length +1,Timestamp.valueOf(LocalDateTime.now()));

            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next())
                    id = rs.getInt(1);
            }
        } catch (SQLException | IllegalAccessException ee) {
            ee.printStackTrace();
            return -2;
        } finally {
            try {
                if (rs != null)preparedStatement.close();
            } catch (SQLException eee) {
                eee.printStackTrace();
                return -2;
            }

        }
        return id;
    }

    public int update(Class<?> c, Map<String, Object> o, int id, Map<String, Integer> typesSQL) {
        Field[] fields = c.getDeclaredFields();
        PreparedStatement preparedStatement = null;
        String update = "";
        for(Map.Entry<String, Object> entry: o.entrySet()) {
            update = update.concat(entry.getKey() + " = ? , ");
        }

        String sql = "UPDATE "+ c.getSimpleName().toLowerCase()+" SET "+update+" updated_at = ? WHERE id = "+id;

        int rowAffected = -1;
        ResultSet rs = null;
        try {
            preparedStatement = super.getPreparedStatement(sql,Statement.RETURN_GENERATED_KEYS);

            int i = 1;
            for(Map.Entry<String, Object> entry: o.entrySet()) {
                preparedStatement.setObject(i++,entry.getValue(),typesSQL.get(entry.getKey()));
            }

            preparedStatement.setTimestamp(i,Timestamp.valueOf(LocalDateTime.now()));
            System.out.println(sql);
            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("OK");
                    System.out.println(rs.getInt(1));
                    rowAffected = rs.getInt(1);
                }
                else {
                    System.out.println("Not anything Generated Keys");
                    rowAffected = 1;
                }


            }
        } catch (SQLException ee) {
            ee.printStackTrace();
            return -2;
        } finally {
            try {
                if (rs != null)preparedStatement.close();
            } catch (SQLException eee) {
                eee.printStackTrace();
                return -2;
            }

        }
        return rowAffected;
    }

    public int delete(Class<?> c, int id) {
        try {
            Statement stmt = getStatement();
            return stmt.executeUpdate("DELETE FROM " + c.getSimpleName().toLowerCase() + " WHERE ID = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
            return -2;
        }
    }
}
