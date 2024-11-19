package user;

import data.CsvCompatible;

public class Staff extends User implements CsvCompatible {
    private int age;
    private String gender;

    public Staff(UserId id, String name, Role role, String gender, int age) {
        super(id, name, role);
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toCsv() {
        return String.format("%s,%s,%s,%s,%d", id, name, role, gender, age);
    }
}
