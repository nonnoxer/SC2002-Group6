package menus;

import java.util.Scanner;
import data.LogInController;
import javax.print.DocFlavor;
import data.ReadFile;

import java.io.IOException;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;

import user.Doctor;
import user.Patient;
import user.Staff;
import user.User;

public class LoginMenu {
    static final Scanner scanner = new Scanner(System.in);
    private static User tempUser = null;
    private static Menu menu = null;
    
    public static void showLoginMenu() {
        String userType = "User not found";
        String username = " ";
        System.out.println("Welcome to the Hospital Management System (HMS)");
        System.out.println("password is always password, no implementation for new password yet");

        while (userType.equals("User not found")) {
            username = promptForInput("Enter username: ");
            String password = promptForInput("Enter password: ");
            userType = LogInController.authenticateUser(username, password);

            if (userType.equals("User not found")) {
                System.out.println("Wrong credentials, try again.\n");
            }
        }

        System.out.println("Login successful! User type: " + userType);


        try {
            Patient[] patients = ReadFile.readPatientListFile("Patient_List.csv");
            Staff[] staffs = ReadFile.readStaffListFile("Staff_List.csv");
            // does not have any condition to check if users have same name.
            switch (userType) {
                case "Patient":
                    setUserAndMenu(username, patients, new PatientMenu());
                    break;

                case "Doctor":
                    setUserAndMenu(username, staffs, new DoctorMenu());
                    break;

                case "Pharmacist":
                    setUserAndMenu(username, staffs, new PharmacistMenu());

                case "Administrator":
                    setUserAndMenu(username, staffs, new AdministratorMenu());

                default:
                    throw new AssertionError();
            }

        } catch (IOException e) {
        System.err.println("Error reading csv: " + e.getMessage());
        }

        menu.showMenu(tempUser);        //polymorphing to showmenu
    }







    private static void setUserAndMenu(String username, User[] users, Menu menu) {
        for (User user : users) {
            if (username.equals(user.getName())) {
                tempUser = user;
                LoginMenu.menu = menu; // Set the appropriate menu using upcasting
                break;
            }
        }
    }


    private static String promptForInput(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

}

    

