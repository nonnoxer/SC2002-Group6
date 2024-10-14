package user;

public class Staff extends User {
    private int age;

    public Staff(String id, String name, String role, String gender, int age) {
        super(id, gender, name, role);
        this.age = age;
    }

    public String getName() {
        return this.name;
    }
}
