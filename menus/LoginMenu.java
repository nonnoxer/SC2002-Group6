package menus;

import data.user.UserDatabase;
import user.Administrator;
import user.Doctor;
import user.Patient;
import user.Pharmacist;
import user.Role;
import user.UserAccount;
import user.UserId;

public class LoginMenu {
    private SafeScanner sc;
    private UserDatabase db;

    public LoginMenu(SafeScanner sc, UserDatabase db) {
        this.sc = sc;
        this.db = db;
    }

    public Menu login() {
        UserAccount account = null;
        System.out.println("Welcome to the Hospital Management System (HMS)");

        while (account == null) {
            String username = promptForInput("Enter username: ");
            account = db.getAccount(username);
            if (account == null) continue;

            if (account.passwordEmpty()) {
                setPassword(account);
            } else {
                String password = promptForInput("Enter password: ");
                if (!account.checkPassword(password)) {
                    System.out.println("Wrong credentials");
                    return null;
                }
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

    private void setPassword(UserAccount account) {
        String password = promptForInput("Enter a new password: ");
        account.setPassword(password);
    }

    private String promptForInput(String message) {
        System.out.print(message);
        return sc.nextLine();
    }
}

    

