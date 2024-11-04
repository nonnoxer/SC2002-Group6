package user;

import data.CsvCompatible;

public class UserAccount implements CsvCompatible {
    private String username, password, role, id;

    public UserAccount(String id, String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.id = id;
    }

    public String toCsv() {
        return String.format("%s,%s,%s,%s", id, username, password, role);
    }

    public String getUsername() {
        return this.username;
    }

    public String getRole() {
        return this.role;
    }

    public String getId() {
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
