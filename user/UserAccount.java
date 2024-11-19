package user;

import data.CsvCompatible;

public class UserAccount implements CsvCompatible {
    private String username, password;
    UserId id;
    private Role role;

    public UserAccount(UserId id, String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.id = id;
    }

    public String toCsv() {
        return String.format("%s,%s,%s,%s", id.toString(), username, password, role);
    }

    public String getUsername() {
        return this.username;
    }

    public Role getRole() {
        return this.role;
    }

    public UserId getId() {
        return this.id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean passwordEmpty() {
        return this.password == "";
    }
    
    public boolean checkPassword(String password) {
        return this.password == password;
    }
}
