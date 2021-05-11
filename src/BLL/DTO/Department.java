package BLL.DTO;

import java.util.Map;

public class Department {
    private int id;
    private String name;
    private int min_salary;
    private int max_salary;

    public Department(String name, int min_salary, int max_salary) {
        this.name = name;
        this.min_salary = min_salary;
        this.max_salary = max_salary;
    }

    public Department(Map<String,Object> map) {
        this.id = (int)map.get("id");
        this.name = String.valueOf(map.get("name"));
        this.min_salary = Integer.parseInt(String.valueOf(map.get("min_salary")));
        this.max_salary = Integer.parseInt(String.valueOf(map.get("max_salary")));
    }

    public Department(Map<String,Object> map,boolean noID) {
        this.name = String.valueOf(map.get("name"));
        this.min_salary = Integer.parseInt(String.valueOf(map.get("min_salary")));
        this.max_salary = Integer.parseInt(String.valueOf(map.get("max_salary")));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMin_salary() {
        return min_salary;
    }

    public void setMin_salary(int min_salary) {
        this.min_salary = min_salary;
    }

    public int getMax_salary() {
        return max_salary;
    }

    public void setMax_salary(int max_salary) {
        this.max_salary = max_salary;
    }

    public String[] toStrings() {
        return new String[] {
                String.valueOf(id),
                name,
                String.valueOf(min_salary),
                String.valueOf(max_salary)
        };
    }
}
