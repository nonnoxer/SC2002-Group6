package user;

/**
 * Abstract class representing a user in the system.
 * This class serves as a base class for different types of users such as patients, doctors, pharmacists, and administrators.
 * It encapsulates common properties and behavior shared by all users, including their ID, name, and role.
 * 
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public abstract class User {
    protected UserId id;
    protected String name;
    protected Role role;

    /**
     * Construct a new User object with the provided ID, name, and role.
     * This constructor initializes the user with their unique identifier, name, and role in the system.
     *
     * @param id The unique ID of the user.
     * @param name The name of the user.
     * @param role The role of the user (e.g., Patient, Doctor, Pharmacist, Administrator).
     */
    public User(UserId id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    /**
     * Get the unique ID of the user.
     *
     * @return The unique ID of the user.
     */
    public UserId getId() {
        return id;
    }

    /**
     * Get the name of the user.
     *
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the role of the user.
     *
     * @return The role of the user.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Set the name of the user.
     *
     * @param name The new name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the role of the user.
     *
     * @param role The new role of the user.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Convert the user's data to a CSV format string.
     * This method should be implemented by subclasses to return their specific CSV representation.
     *
     * @return A string in CSV format representing the user's details.
     */
    public abstract String toCsv();
}
