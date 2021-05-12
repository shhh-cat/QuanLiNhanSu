package BLL.DTO;

import java.time.LocalDate;
import java.util.Map;

public class Employee {
    private int id;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String address;
    private boolean gender;
    private String idnumber;
    private int department_id;
    private String position;
    private int bonus_position;
    private int salary;
    private int project_id;
    private LocalDate start_date;


    public Employee(int id, String firstname, String lastname, String phonenumber, String address, boolean gender, String idnumber, int department_id, String position, int bonus_position, int salary, int project_id, LocalDate start_date) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.address = address;
        this.gender = gender;
        this.idnumber = idnumber;
        this.department_id = department_id;
        this.position = position;
        this.bonus_position = bonus_position;
        this.salary = salary;
        this.project_id = project_id;
        this.start_date = start_date;
    }

    public Employee(Map<String, Object> map) {
        if (map.containsKey("id"))
            this.id = (int)(map.get("id"));
        this.firstname = String.valueOf(map.get("firstname"));
        this.lastname = String.valueOf(map.get("lastname"));
        this.phonenumber = String.valueOf(map.get("phonenumber"));
        this.address = String.valueOf(map.get("address"));
        this.gender = (boolean)map.get("gender");
        this.idnumber = String.valueOf(map.get("idnumber"));
        this.department_id = Integer.parseInt(String.valueOf(map.get("department_id")));
        this.position = String.valueOf(map.get("position"));
        this.bonus_position = Integer.parseInt(String.valueOf(map.get("bonus_position")));
        this.salary = Integer.parseInt(String.valueOf(map.get("salary")));
        this.project_id = Integer.parseInt(String.valueOf(map.get("project_id")));
        this.start_date = (LocalDate)  map.get("start_date");
    }


    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }


    public String getIdnumber() {
        return idnumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getBonus_position() {
        return bonus_position;
    }

    public void setBonus_position(int bonus_position) {
        this.bonus_position = bonus_position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String[] toStrings() {
        return new String[] {
                String.valueOf(id),
                firstname,
                lastname,
                phonenumber,
                address,
                gender ? "Nam" : "Nữ",
                idnumber,
                String.valueOf(department_id),
                position,
                String.valueOf(bonus_position),
                String.valueOf(salary),
                String.valueOf(project_id),
                start_date.toString()
        };
    }
}
