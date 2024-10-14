package user;

public class User {
    private String id;
    private String password;
    private String name;
    private String role;

    public User(String id, String password, String name, String role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
