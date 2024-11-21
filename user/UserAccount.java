package user;

import data.CsvCompatible;

/**
 * The UserAccount class represents a user account in the system.
 * A user account holds the login credentials (username, password) and the role of the user.
 * It is used for authentication and authorization within the system.
 * 
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class UserAccount implements CsvCompatible {
    private String username, password;
    UserId id;
    private Role role;

    /**
     * Construct a new UserAccount with the provided user ID, username, password, and role.
     * This constructor initializes the account with the specified details.
     *
     * @param id The unique user ID associated with the account.
     * @param username The username for login.
     * @param password The password for login.
     * @param role The role of the user (e.g., Patient, Doctor, Pharmacist, Administrator).
     */
    public UserAccount(UserId id, String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.id = id;
    }

    /**
     * Convert the user account's data to a CSV format string.
     * The resulting string contains the user ID, username, password, and role.
     *
     * @return A string in CSV format representing the user's account details.
     */
    public String toCsv() {
        return String.format("%s,%s,%s,%s", id.toString(), username, password, role);
    }

    /**
     * Get the username associated with the user account.
     *
     * @return The username of the user account.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the role of the user associated with the account.
     *
     * @return The role of the user
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * Get the unique ID associated with the user account.
     *
     * @return The unique user ID.
     */
    public UserId getId() {
        return this.id;
    }

    /**
     * Set a new password for the user account.
     *
     * @param password The new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Check if the password is empty.
     *
     * @return True if the password is an empty string, otherwise false.
     */
    public boolean passwordEmpty() {
        return this.password == "";
    }
    
    /**
     * Check if the provided password matches the stored password for this account.
     *
     * @param password The password to check.
     * @return True if the provided password matches the stored password, otherwise false.
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
