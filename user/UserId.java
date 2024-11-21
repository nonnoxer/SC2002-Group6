package user;

/**
 * Represents a unique user identifier in the system, consisting of a character prefix and a numeric part.
 * The prefix is typically used to categorize users (e.g., 'P' for patient, 'D' for doctor), 
 * while the numeric part uniquely identifies each user.
 * 
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class UserId {
    private char prefix;
    private int num;

    /**
     * Constructs a UserId object from a string representation.
     * The string should start with a character (prefix) followed by a numeric value.
     * If the string format is invalid, an IllegalArgumentException is thrown.
     *
     * @param id The string representing the user ID, where the first character is the prefix and the rest is the number.
     * @throws IllegalArgumentException If the string does not have a valid format or if the numeric part is not a valid integer.
     */
    public UserId(String id) throws IllegalArgumentException {
        if (id.length() < 2) {
            throw new IllegalArgumentException("User ID should have at least a character and a number");
        }
        try {
            this.prefix = id.charAt(0);
            this.num = Integer.parseInt(id.substring(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(id.substring(1) + " is not a valid number.");
        }
    }

    /**
     * Constructs a UserId object from a given character prefix and numeric value.
     *
     * @param prefix The character prefix that categorizes the user (e.g., 'P' for Patient, 'D' for Doctor).
     * @param num The unique numeric identifier for the user.
     */
    public UserId(char prefix, int num) {
        this.prefix = prefix;
        this.num = num;
    }

    /**
     * Converts the UserId to its string representation.
     * The string consists of the prefix followed by the numeric part, formatted with leading zeros if necessary.
     *
     * @return A string representing the user ID (e.g., "P001" for Patient with ID 1).
     */
    public String toString() {
        return String.format("%c%03d", prefix, num);
    }
    
    /**
     * Gets the character prefix of the UserId.
     * The prefix typically represents a category of users (e.g., 'P' for Patient).
     *
     * @return The character prefix of the user ID.
     */
    public char getPrefix() {
        return this.prefix;
    }

    /**
     * Gets the numeric part of the UserId.
     * The numeric part is a unique identifier for the user within their category.
     *
     * @return The numeric part of the user ID.
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Returns a hash code value for the UserId.
     * The hash code is computed based on the string representation of the UserId.
     *
     * @return The hash code of the UserId.
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Checks whether two UserId objects are equal.
     * Two UserIds are considered equal if their string representations are identical.
     *
     * @param o The object to compare with.
     * @return True if the UserId objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }
}
