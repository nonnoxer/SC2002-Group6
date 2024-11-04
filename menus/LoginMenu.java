package menus;

import java.util.Scanner;
import data.ReadFile;
import data.UserDatabase;

import java.io.IOException;

import user.Administrator;
import user.Doctor;
import user.Patient;
import user.Pharmacist;
import user.Staff;
import user.UserAccount;

public class LoginMenu {
    static final Scanner scanner = new Scanner(System.in);
    private static UserDatabase db;
    private static Patient[] patients;
    private static Staff[] staffs;
    private static Menu menu = null;
    
    public static void showLoginMenu() {
        try {
            patients = ReadFile.readPatientListFile("Patient_List.csv");
            staffs = ReadFile.readStaffListFile("Staff_List.csv");
            db = new UserDatabase("Account_List.csv", patients, staffs);
        } catch (IOException e) {
            System.out.println("Error reading or writing CSVs. Please check your local files.");
            e.printStackTrace();
            return;
        }

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
                    return;
                }
            }
        }

        String id = account.getId(), role = account.getRole();
        System.out.println("Login successful! User type: " + role);

        switch (role) {
            case "Patient":
                Patient patient = db.findPatientId(patients, id);
                menu = new PatientMenu(scanner, patient);
                break;
            // Legal to downcast as staffs is contains Doctor, Pharmacist and Administrator
            case "Doctor":
                Doctor doctor = (Doctor) db.findStaffId(staffs, id);
                menu = new DoctorMenu(scanner, doctor);
                break;
            case "Pharmacist":
                Pharmacist pharmacist = (Pharmacist) db.findStaffId(staffs, id);
                menu = new PharmacistMenu(scanner, pharmacist);
                break;
            case "Administrator":
                Administrator administrator = (Administrator) db.findStaffId(staffs, id);
                menu = new AdministratorMenu(scanner, administrator);
                break;
            default:
                throw new AssertionError();
        }

        menu.showMenu();
    }

    private static void setPassword(UserAccount account) {
        String password = promptForInput("Enter a new password: ");
        account.setPassword(password);
    }

    private static String promptForInput(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

}

    

