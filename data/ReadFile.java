package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import medicine.Medicine;
import user.Administrator;
import user.Doctor;
import user.Patient;
import user.Pharmacist;
import user.Staff;

public class ReadFile {
    private static String[][] readCSV(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        String[][] lines_separated = new String[lines.size()][];
        for (int i = 0; i < lines_separated.length; i++) {
            lines_separated[i] = lines.get(i).split(",");
        }
        return lines_separated;
    }
    
    public static Staff[] readStaffListFile(String path) throws IOException {
        String[][] values = ReadFile.readCSV(path);

        Staff[] staffs = new Staff[values.length];
        for (int i = 0; i < values.length; i++) {
            String[] line = values[i];
            if (line.length != 5) {
                String line_full = String.join(",", line);
                throw new IOException("Invalid line " + line_full + ": expected 5 elements.");
            }
            String id = line[0], name = line[1], role = line[2], gender = line[3];
            int age;
            try {
                age = Integer.parseInt(line[4]);
            } catch (NumberFormatException e) {
                throw new IOException("Invalid line: expected " + line[4] + " to be an integer.");
            }

            Staff staff;
            switch (role) {
                case "Doctor":
                    staff = new Doctor(id, name, role, gender, age);
                    break;
                case "Pharmacist":
                    staff = new Pharmacist(id, name, role, gender, age);
                    break;
                case "Administrator":
                    staff = new Administrator(id, name, role, gender, age);
                    break;
                default:
                    throw new IOException("Invalid line: expected " + line[2] + " to be one of 'Doctor', 'Pharmacist', 'Administrator'.");
            }

            staffs[i] = staff;
        }

        return staffs;
    }
    
    public static Patient[] readPatientListFile(String path) throws IOException {
        String[][] values = ReadFile.readCSV(path);

        Patient[] patients = new Patient[values.length];
        for (int i = 0; i < values.length; i++) {
            String[] line = values[i];
            if (line.length != 6) {
                String line_full = String.join(",", line);
                throw new IOException("Invalid line " + line_full + ": expected 6 elements.");
            }
            String id = line[0], name = line[1], birthDate = line[2], gender = line[3], bloodType = line[4], contactInfo = line[5];

            patients[i] = new Patient(id, name, birthDate, gender, bloodType, contactInfo);
        }

        return patients;
    }

    public static Medicine[] readMedicineListFile(String path) throws IOException {
        String[][] values = ReadFile.readCSV(path);

        Medicine[] inventory = new Medicine[values.length];
        for (int i = 0; i < values.length; i++) {
            String[] line = values[i];
            if (line.length != 3) {
                String line_full = String.join(",", line);
                throw new IOException("Invalid line " + line_full + ": expected 3 elements.");
            }
            String name = line[0];
            int initialStock, lowStockLevelAlert;
            try {
                initialStock = Integer.parseInt(line[1]);
            } catch (NumberFormatException e) {
                throw new IOException("Invalid line: expected " + line[1] + " to be an integer.");
            }
            try {
                lowStockLevelAlert = Integer.parseInt(line[2]);
            } catch (NumberFormatException e) {
                throw new IOException("Invalid line: expected " + line[2] + " to be an integer.");
            }

            inventory[i] = new Medicine(name, initialStock, lowStockLevelAlert);
        }

        return inventory;
    }
}

