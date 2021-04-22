package BLL.DTO;

import java.util.Map;

public class Employee {
    private int id;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String address;
    private String ward;
    private String district;
    private String country;
    private boolean gender;
    private String ethnicity;
    private String religion;
    private String idnumber;

    public Employee( String firstname, String lastname, String phonenumber, String address, String ward, String district, String country, boolean gender, String ethnicity, String religion, String idnumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.address = address;
        this.ward = ward;
        this.district = district;
        this.country = country;
        this.gender = gender;
        this.ethnicity = ethnicity;
        this.religion = religion;
        this.idnumber = idnumber;
    }
    public Employee(Map<String, Object> map) {
        this.id = (int)map.get("id");
        this.firstname = String.valueOf(map.get("firstname"));
        this.lastname = String.valueOf(map.get("lastname"));
        this.phonenumber = String.valueOf(map.get("phonenumber"));
        this.address = String.valueOf(map.get("address"));
        this.ward = String.valueOf(map.get("ward"));
        this.district = String.valueOf(map.get("district"));
        this.country = String.valueOf(map.get("country"));
        this.gender = (boolean)map.get("gender");
        this.ethnicity = String.valueOf(map.get("ethnicity"));
        this.religion = String.valueOf(map.get("religion"));
        this.idnumber = String.valueOf(map.get("idnumber"));
    }

    public Employee(Map<String, Object> map,boolean noId) {
        this.firstname = String.valueOf(map.get("firstname"));
        this.lastname = String.valueOf(map.get("lastname"));
        this.phonenumber = String.valueOf(map.get("phonenumber"));
        this.address = String.valueOf(map.get("address"));
        this.ward = String.valueOf(map.get("ward"));
        this.district = String.valueOf(map.get("district"));
        this.country = String.valueOf(map.get("country"));
        this.gender = (boolean)map.get("gender");
        this.ethnicity = String.valueOf(map.get("ethnicity"));
        this.religion = String.valueOf(map.get("religion"));
        this.idnumber = String.valueOf(map.get("idnumber"));
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

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getIdnumber() {
        return idnumber;
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
                ward,
                district,
                country,
                gender ? "Nam" : "Nữ",
                ethnicity,
                religion,
                idnumber
        };
    }
}
