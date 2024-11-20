package menus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.user.UserDatabase;
import user.Patient;
import user.UserAccount;

public class RegisterMenu {
    private SafeScanner sc;
    private UserDatabase db;

    public RegisterMenu(SafeScanner sc, UserDatabase db) {
        this.sc = sc;
        this.db = db;
    }

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

    public static boolean isValidGender(String gender) {
        return gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female");
    }

    private static boolean isValidBloodType(String bloodType) {
        String regex = "^(A|B|AB|O)[+-]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(bloodType);
        return matcher.matches();
    }

    private static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
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
