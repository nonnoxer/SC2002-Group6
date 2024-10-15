package menus;

import java.util.Scanner;
import data.LogInController;
import javax.print.DocFlavor;
import data.ReadFile;

import java.io.IOException;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.List; 

import user.Patient;
import user.User;

public class LoginMenu {
    static final Scanner scanner = new Scanner(System.in);
    
    public static void showLoginMenu() {
        String userType = null;
        String username = " ";
        System.out.println("Welcome to the Hospital Management System (HMS)");

        while (userType == null) {
            username = promptForInput("Enter username: ");
            String password = promptForInput("Enter password: ");
            userType = LogInController.authenticateUser(username, password);

            if (userType == null) {
                System.out.println("Wrong credentials, try again.\n");
            }
        }

        System.out.println("Login successful! User type: " + userType);
        User tempUser = null;
        Menu menu = null;
        try {
            switch (userType) {
                case "patient":

                Patient[] patients = ReadFile.readPatientListFile();
                    for(Patient patient : patients){
                        if(username.equals(patient.getName())){
                            tempUser = patient;
                            menu = new PatientMenu();
                        }
                    }
                    break;

                case "doctor":


                default:
                    throw new AssertionError();
            }
        } catch (IOException e) {
        System.err.println("Error reading patient list: " + e.getMessage());
        }
        scanner.close();
        menu.showMenu(tempUser);
    }
        
    








    private static String promptForInput(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

}

    

