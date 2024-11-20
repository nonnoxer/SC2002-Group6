package menus;

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
        String birthDate = sc.promptLine("Enter birth date: ");
        String gender = sc.promptLine("Enter gender: ");
        String bloodType = sc.promptLine("Enter blood type: ");
        String contactInfo = sc.promptLine("Enter email: ");

        Patient patient = db.registerPatient(username, name, birthDate, gender, bloodType, contactInfo);

        db.setPassword(patient.getId(), password);

        System.out.println("Register successful! User type: Patient");
        return new PatientMenu(sc, patient);
    }

    private String getPassword() {
        String password = sc.promptLine("Enter a new password: ");;
        while (password.equals("")){
            System.out.println("Password cannot be blank.");
            password = sc.promptLine("Enter a new password: ");
        }
        return password;
    }
}
