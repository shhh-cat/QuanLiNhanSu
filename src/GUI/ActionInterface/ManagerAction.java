package GUI.ActionInterface;

import java.awt.*;
import java.util.Map;
import java.util.Vector;

public interface ManagerAction {
    public void add(Map<String, Object> values);
    public void update(Map<String, Object> newValues, int selectedRow, Map<String, Object> oldValues);
    public void delete(Map<String, Object> data, int selectedRow);
    public void find();
    public void sort(String column,int sort);
    public void showAll();
    public void back();
}
