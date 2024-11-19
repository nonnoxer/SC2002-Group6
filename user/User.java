package user;

public abstract class User {
    protected UserId id;
    protected String name;
    protected Role role;
    private String password;

    // Constructor to return id, password, name etc
    public User(UserId id, String password, String name, Role role) {
        this.id = id;
        this.password = password;
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

    // Setter for updating password
    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public abstract String toCsv();
}
