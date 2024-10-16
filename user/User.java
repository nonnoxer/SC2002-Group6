package user;

public abstract class User {
    protected String id, name, role;
    private String password;

    // Constructor to return id, password, name etc
    public User(String id, String password, String name, String role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    // Getters
    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    // Setter for updating password
    public void setPassword(String password) {
        this.password = password;
    }

    public abstract String toString();
}
