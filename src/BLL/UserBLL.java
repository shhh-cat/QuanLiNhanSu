package BLL;

import DAL.Eloquent.Eloquent;
import DAL.EmployeeDAL;

public class UserBLL {
    public static String[][] search(String key, Object value) {
        if (key.equals("id") || key.equals("gender"))
            return EmployeeDAL.get(key,value, Eloquent.EQUAL);
        return EmployeeDAL.get(key,value, Eloquent.LIKE);
    }
}
