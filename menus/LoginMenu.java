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

    private void setPassword(UserId id) {
        String password = sc.promptLine("Enter a new password: ");;
        while (password.equals("")){
            System.out.println("Password cannot be blank.");
            password = sc.promptLine("Enter a new password: ");
        }
        
        db.setPassword(id, password);
    }

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
}

    

