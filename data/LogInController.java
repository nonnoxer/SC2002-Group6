package data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import user.Patient;

public class LogInController {
    public static String authenticateUser(String username, String password) {

        if (!password.equals("password")) {
            return "Invalid password";
        }

        String role = checkUserRole(username);
        return role != null ? role : "User not found";
    }
    private static String checkUserRole(String username) {
        try {
            // Check PatientList.csv
            BufferedReader patientReader = new BufferedReader(new FileReader("Patient_List.csv"));
            String line;
            while ((line = patientReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(username)) {
                    patientReader.close();
                    return "Patient";
                }
            }
            patientReader.close();

            // Check StaffList.csv
            BufferedReader staffReader = new BufferedReader(new FileReader("Staff_List.csv"));
            while ((line = staffReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[1].equals(username)) {
                    String role = data[2]; // Role is in the third column
                    staffReader.close();
                    return role.toLowerCase(); // Return role as lowercase
                }
            }
            staffReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // User not found
    }
}


