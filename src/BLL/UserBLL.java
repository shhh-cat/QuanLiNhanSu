package BLL;

import DAL.Eloquent.Eloquent;
import DAL.UserDAL;

public class UserBLL {
    public static String[][] search(String key, Object value) {
        return UserDAL.get(key,value, Eloquent.EQUAL);
    }
}
