package menus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.user.UserDatabase;
import user.Patient;
import user.UserAccount;

/**
 * The RegisterMenu class handles the user registration process for the Hospital Management System (HMS).
 * It allows a new user to register by providing required details such as username, password, name, birth date,
 * gender, blood type, and contact information (email). It also includes validation for the input data.
 * 
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class RegisterMenu {
    private SafeScanner sc;
    private UserDatabase db;

    /**
     * Constructs a new RegisterMenu with the specified SafeScanner and UserDatabase.
     *
     * @param sc the SafeScanner used for reading user input
     * @param db the UserDatabase used for storing and retrieving user accounts
     */
    public RegisterMenu(SafeScanner sc, UserDatabase db) {
        this.sc = sc;
        this.db = db;
    }

    /**
     * Registers a new user by prompting for user details, performing necessary validation,
     * and storing the user information in the database.
     * 
     * @return a new PatientMenu after successful registration
     */
    public Menu register() {
        UserAccount account = null;
        System.out.println("Welcome to the Hospital Management System (HMS)");

        boolean usernameTaken = true;
        String username = "";
        while (usernameTaken) {
            username = sc.promptLine("Enter new username: ");
            account = db.getAccount(username);
            if (account != null) {
                System.out.println("Username already taken. Please try again.");
            } else {
                usernameTaken = false;
            }
        }

        String password = getPassword();

        String name = sc.promptLine("Enter name: ");
        // birthdate + validation
        String birthDate = sc.promptLine("Enter birth date (YYYY-MM-DD): ");
        while (!isValidDate(birthDate)) {
            System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            birthDate = sc.promptLine("Enter birth date (YYYY-MM-DD): ");
        }
        // gender + validation
        String gender = sc.promptLine("Enter gender (Male/Female): ");
        while (!isValidGender(gender)) {
            System.out.println("Invalid input. Please enter either 'Male' or 'Female'.");
            gender = sc.promptLine("Enter gender (Male/Female): ");
        }
        // bloodType + validation
        String bloodType = sc.promptLine("Enter blood type (e.g., A+, O-, B+, AB-): ");
        while (!isValidBloodType(bloodType)) {
            System.out.println("Invalid blood type. Please enter a valid blood type (e.g., A+, O-, B+, AB-).");
            bloodType = sc.promptLine("Enter blood type (e.g., A+, O-, B+, AB-): ");
        }
        // contactInfo + email validation
        String contactInfo = sc.promptLine("Enter email: ");
        while (!isValidEmail(contactInfo)) {
            System.out.println("Invalid email. Please enter a valid email address.");
            contactInfo = sc.promptLine("Enter email: ");
        }
        
        Patient patient = db.registerPatient(username, name, birthDate, gender, bloodType, contactInfo);

        db.setPassword(patient.getId(), password);

        System.out.println("Register successful! User type: Patient");
        return new PatientMenu(sc, patient);
    }

    /**
     * Prompts the user for a password and validates that it meets the complexity requirements:
     * - At least 8 characters long
     * - Contains at least one uppercase letter
     * - Contains at least one lowercase letter
     * - Contains at least one digit
     * - Contains at least one special character (e.g., @, #, $, %)
     * 
     * @return a valid password entered by the user
     */
    private String getPassword() {
        String password = sc.promptLine("Enter a new password: ");
        
        while (password.equals("") || !isValidPassword(password)) {
            if (password.equals("")) {
                System.out.println("Password cannot be blank.");
            } else {
                System.out.println("Password must meet the following criteria: ");
                System.out.println("- At least 8 characters long.");
                System.out.println("- Contains at least one uppercase letter.");
                System.out.println("- Contains at least one lowercase letter.");
                System.out.println("- Contains at least one digit.");
                System.out.println("- Contains at least one special character (e.g., @, #, $, %).");
            }
            password = sc.promptLine("Enter a new password: ");
        }
        
        return password;
    }
    
    /**
     * Validates the format of a birth date in the format YYYY-MM-DD.
     *
     * @param date the birth date to validate
     * @return true if the date format is valid and the date is real, false otherwise
     */
    private static boolean isValidDate(String date) {
        String regex = "^(\\d{4})-(\\d{2})-(\\d{2})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        if (matcher.matches()) {
            int year = Integer.parseInt(matcher.group(1));
            int month = Integer.parseInt(matcher.group(2));
            int day = Integer.parseInt(matcher.group(3));

            if (month < 1 || month > 12) {
                return false;
            }

            int daysInMonth = getDaysInMonth(month, year);
            return day >= 1 && day <= daysInMonth;
        }
        return false;
    }

    /**
     * Returns the number of days in a given month of a specific year.
     * 
     * @param month the month (1-12)
     * @param year the year
     * @return the number of days in the month
     */
    public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
            default:
                return 0;
        }
    }

    /**
     * Validates the gender input, ensuring that it is either "Male" or "Female".
     *
     * @param gender the gender to validate
     * @return true if the gender is either "Male" or "Female", false otherwise
     */
    public static boolean isValidGender(String gender) {
        return gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female");
    }

    /**
     * Validates the blood type input, ensuring it matches the format of a valid blood type (e.g., A+, O-, B+).
     *
     * @param bloodType the blood type to validate
     * @return true if the blood type matches the valid format, false otherwise
     */
    private static boolean isValidBloodType(String bloodType) {
        String regex = "^(A|B|AB|O)[+-]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(bloodType);
        return matcher.matches();
    }

    /**
     * Validates the email input, ensuring it matches a valid email format.
     *
     * @param email the email to validate
     * @return true if the email matches a valid format, false otherwise
     */
    private static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    /**
     * Validates the password input, ensuring it meets specific complexity requirements.
     * 
     * @param password the password to validate
     * @return true if the password meets the complexity criteria, false otherwise
     */
    private boolean isValidPassword(String password) {
        // Regular expression to check password complexity:
        // - At least 8 characters long
        // - At least one uppercase letter
        // - At least one lowercase letter
        // - At least one digit
        // - At least one special character
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
