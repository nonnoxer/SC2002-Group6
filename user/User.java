package user;

public abstract class User {
    protected UserId id;
    protected String name;
    protected Role role;

    // Constructor to return id, password, name etc
    public User(UserId id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    // Getters
    public UserId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public abstract String toCsv();
}
