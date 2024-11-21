package user;

import data.CsvCompatible;

/**
 * The Staff class represents a Staff member in the system, extending the User class and implementing the CsvCompatible interface.
 * A staff member has personal information such as name, age, and gender, and is assigned a role in the system.
 * This class is the base for other staff roles such as doctors, pharmacists, and administrators.
 * 
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class Staff extends User implements CsvCompatible {
    private int age;
    private String gender;

    /**
     * Construct a new Staff object with the provided details.
     *
     * @param id The unique user ID of the staff member.
     * @param name The name of the staff member.
     * @param role The role of the staff member (e.g., Doctor, Pharmacist, Administrator).
     * @param gender The gender of the staff member.
     * @param age The age of the staff member.
     */
    public Staff(UserId id, String name, Role role, String gender, int age) {
        super(id, name, role);
        this.age = age;
        this.gender = gender;
    }

    /**
     * Get the name of the staff member.
     *
     * @return The name of the staff member.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the age of the staff member.
     *
     * @return The age of the staff member.
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Get the gender of the staff member.
     *
     * @return The gender of the staff member.
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Set the gender of the staff member.
     *
     * @param gender The new gender of the staff member.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Set the age of the staff member.
     *
     * @param age The new age of the staff member.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Convert the staff member's data to a CSV format string.
     *
     * @return A string in CSV format representing the staff member's details.
     */
    public String toCsv() {
        return String.format("%s,%s,%s,%s,%d", id, name, role, gender, age);
    }
}
