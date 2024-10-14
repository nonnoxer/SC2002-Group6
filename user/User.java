package user;

import java.io.FileWriter;
import java.io.IOException;

public class User {
    private String id;
    private String password;
    private String name;
    private String role;

    // Constructor to return id, password, name etc
    public User(String id, String password, String name, String role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    // Getters
    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    // Setter for updating password
    public void setPassword(String password) {
        this.password = password;
    }

    // Save updated credentials to the respective CSV file
    public void saveToCSV() {
        String fileName = "";
        switch (role.toLowerCase()) {
            case "doctor":
                fileName = "Staff_List.csv";
                break;
            case "patient":
                fileName = "Patient_List.csv";
                break;
            case "pharmacist":
                fileName = "Staff_List.csv";
                break;
            case "administrator":
                fileName = "Staff_List.csv";
                break;
            default:
                System.out.println("Unknown role: Cannot save to CSV.");
                return;
        }

        //Create a new object writer in order to modify the CSV files
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.append(id).append(",");
            writer.append(password).append(",");
            writer.append(name).append(",");
            writer.append(role).append("\n");
            System.out.println("Updated credentials saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage()); //catch any errors or unappropriate requests and return an error message
        }
    }
}
