package user;

import data.CsvCompatible;

public class Staff extends User implements CsvCompatible {
    private int age;
    private String gender;

    public Staff(String id, String name, String role, String gender, int age) {
        super(id, "", name, role);
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return String.format("%s,%s,%s,%s,%d", id, name, role, gender, age);
    }
}
