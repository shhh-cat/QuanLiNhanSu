package BLL.DTO;

import java.util.Map;

public class User {
    int id;
    private String username;
    private String password;
    private String name;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public User(Map<String, Object> map) {
        this.id = (int) map.get("id");
        this.username = String.valueOf(map.get("username"));
        this.password = String.valueOf(map.get("password"));
        this.name = String.valueOf(map.get("name"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] toStrings() {
        return new String[] {
                String.valueOf(id),
                username,
                password,
                name
        };
    }
}
