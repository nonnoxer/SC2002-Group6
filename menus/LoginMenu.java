package menus;

import data.user.UserDatabase;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import user.Administrator;
import user.Doctor;
import user.Patient;
import user.Pharmacist;
import user.Role;
import user.UserAccount;
import user.UserId;

/**
 * This class represents the login menu for the Hospital Management System (HMS).
 * It handles user authentication, password setting, and redirects users to their respective menus based on their role.
 * 
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class LoginMenu {
    private SafeScanner sc;
    private UserDatabase db;

    /**
     * Construct a LoginMenu with the specified SafeScanner and UserDatabase.
     *
     * @param sc  The SafeScanner used for user input.
     * @param db  The UserDatabase that contains user information and credentials.
     */
    public LoginMenu(SafeScanner sc, UserDatabase db) {
        this.sc = sc;
        this.db = db;
    }

    /**
     * Initiates the login process. Prompts the user to enter their username and verifies their password.
     * If the user has no password set, they will be prompted to set one.
     * After successful authentication, the user is redirected to their respective menu based on their role.
     *
     * @return A Menu object corresponding to the user's role, or null if login fails after multiple attempts.
     */
    public Menu login() {
        UserAccount account = null;
        System.out.println("Welcome to the Hospital Management System (HMS)");

        while (account == null) {
            String username = sc.promptLine("Enter username: ");
            account = db.getAccount(username);
            if (account == null) continue;

            if (account.passwordEmpty()) {
                setPassword(account.getId());
            } else {
                boolean result = checkPassword(account, 3);
                if (!result) return null;
            }
        }

        UserId id = account.getId();
        Role role = account.getRole();
        System.out.println("Login successful! User type: " + role.toString());

        Menu menu;

        switch (role) {
            case Patient:
                Patient patient = db.findPatientId(id);
                menu = new PatientMenu(sc, patient);
                break;
            // Legal to downcast as staffs is contains Doctor, Pharmacist and Administrator
            case Doctor:
                Doctor doctor = (Doctor) db.findStaffId(id);
                menu = new DoctorMenu(sc, doctor);
                break;
            case Pharmacist:
                Pharmacist pharmacist = (Pharmacist) db.findStaffId(id);
                menu = new PharmacistMenu(sc, pharmacist);
                break;
            case Administrator:
                Administrator administrator = (Administrator) db.findStaffId(id);
                menu = new AdministratorMenu(sc, administrator);
                break;
            default:
                throw new AssertionError();
        }

        return menu;
    }

    /**
     * Prompts the user to enter a new password and validates the password's complexity.
     * The user must input a valid password meeting specific criteria before it is set.
     *
     * @param id The UserId of the account whose password is being set.
     */
    private void setPassword(UserId id) {
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
        
        db.setPassword(id, password);
        System.out.println("Password successfully updated.");
    }

    /**
     * Prompts the user to enter their password a specified number of times.
     * If the user enters the correct password, the method returns true.
     * If the password is incorrect after the specified number of attempts, it returns false.
     *
     * @param account The UserAccount whose password is being checked.
     * @param tries   The number of attempts allowed for entering the correct password.
     * @return true if the correct password is entered, false otherwise.
     */
    private boolean checkPassword(UserAccount account, int tries) {
        for (int i = 0; i < tries; i++) {
            String password = sc.promptLine("Enter password: ");
            if (account.checkPassword(password)) {
                return true;
            }
            System.out.printf("Incorrect password. %d attempts left.\n", tries-i-1);
        }
        return false;
    }

    /**
     * Validates the complexity of a password using a regular expression.
     * The password must meet the following criteria:
     * - At least 8 characters long.
     * - Contains at least one uppercase letter.
     * - Contains at least one lowercase letter.
     * - Contains at least one digit.
     * - Contains at least one special character.
     *
     * @param password The password string to validate.
     * @return true if the password meets the complexity requirements, false otherwise.
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