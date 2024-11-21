package data.user;

import java.util.ArrayList;

import user.Role;
import user.Staff;
import user.UserId;

/**
 * Interface representing an API for managing staff in a user database, specifically for administrators.
 * It provides methods for retrieving, adding, updating, and removing staff members.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public interface UserDatabaseApiAdministrator {
    /**
     * Retrieves a list of all staff members in the database.
     *
     * @return a list of all staff members
     */
    public abstract ArrayList<Staff> getStaff();

    /**
     * Adds a new staff member to the database with the given details.
     * 
     * @param name the name of the new staff member
     * @param role the role of the new staff member (Doctor, Administrator, Pharmacist, etc.)
     * @param gender the gender of the new staff member
     * @param age the age of the new staff member
     * @return the newly added Staff object
     */
    public abstract Staff addStaff(String name, Role role, String gender, int age);

    /**
     * Updates the details of an existing staff member in the database.
     * Note: The role of a staff member cannot be changed once assigned.
     * 
     * @param id the unique ID of the staff member to update
     * @param name the new name of the staff member
     * @param role the new role of the staff member (Note: role change is typically not allowed)
     * @param gender the new gender of the staff member
     * @param age the new age of the staff member
     * @return the updated Staff object, or null if the staff member with the given ID is not found
     */
    public abstract Staff updateStaff(UserId id, String name, Role role, String gender, int age);

    /**
     * Removes a staff member from the database based on their unique ID.
     *
     * @param id the unique ID of the staff member to remove
     * @return the removed Staff object, or null if the staff member with the given ID is not found
     */
    public abstract Staff removeStaff(UserId id);
}
