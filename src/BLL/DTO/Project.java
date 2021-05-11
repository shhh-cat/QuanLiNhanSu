package BLL.DTO;

import java.time.LocalDate;
import java.util.Map;

public class Project {
    private int id;
    private String name;
    private LocalDate start_date;

    public Project(int id, String name, LocalDate start_date) {
        this.id = id;
        this.name = name;
        this.start_date = start_date;
    }

    public Project(Map<String, Object> map) {
        if (map.containsKey("id"))
            this.id = (int)map.get("id");
        this.name = String.valueOf(map.get("name"));
        this.start_date = (LocalDate) map.get("start_date");
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

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public String[] toStrings() {
        return new String[] {
                String.valueOf(id),
                name,
                start_date.toString(),
        };
    }
}
