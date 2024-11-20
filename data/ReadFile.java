package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import appointment.Appointment;
import medicine.Medicine;
import medicine.ReplenishmentRequest;
import user.Administrator;
import user.Doctor;
import user.Patient;
import user.Pharmacist;
import user.Role;
import user.Staff;
import user.UserAccount;
import user.UserId;

public class ReadFile {
    private static String[][] readCSV(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        String[][] lines_separated = new String[lines.size()][];
        for (int i = 0; i < lines_separated.length; i++) {
            lines_separated[i] = lines.get(i).split(",", -1);
        }
        return lines_separated;
    }

    public static ArrayList<UserAccount> readAccountListFile(String path) throws IOException {
        String[][] values = ReadFile.readCSV(path);

        ArrayList<UserAccount> accounts = new ArrayList<UserAccount>();
        for (int i = 0; i < values.length; i++) {
            String[] line = values[i];
            if (line.length != 4) {
                String line_full = String.join(",", line);
                throw new IOException("Invalid line " + line_full + ": expected 4 elements.");
            }
            String username = line[1], password = line[2];

            UserId id;
            try {
                id = new UserId(line[0]);
            } catch (IllegalArgumentException e) {
                throw new IOException("Invalid line: expected " + line[0] + "to be a user ID.");
            }

            Role role;
            try {
                role = Role.valueOf(line[3]);
            } catch (IllegalArgumentException e) {
                throw new IOException("Invalid line: expected " + line[3] + " to be one of 'Patient', 'Doctor', 'Pharmacist', 'Administrator'.");
            }

            accounts.add(new UserAccount(id, username, password, role));
        }
        return accounts;
    }
    
    public static ArrayList<Staff> readStaffListFile(String path) throws IOException {
        String[][] values = ReadFile.readCSV(path);

        ArrayList<Staff> staffs = new ArrayList<Staff>();
        for (int i = 0; i < values.length; i++) {
            String[] line = values[i];
            if (line.length != 5) {
                String line_full = String.join(",", line);
                throw new IOException("Invalid line " + line_full + ": expected 5 elements.");
            }
            String name = line[1], gender = line[3];

            UserId id;
            try {
                id = new UserId(line[0]);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new IOException("Invalid line: expected " + line[0] + "to be a user ID.");
            }

            Role role = Role.valueOf(line[2]);
            int age;
            try {
                age = Integer.parseInt(line[4]);
            } catch (NumberFormatException e) {
                throw new IOException("Invalid line: expected " + line[4] + " to be an integer.");
            }

            Staff staff;
            switch (role) {
                case Doctor:
                    staff = new Doctor(id, name, role, gender, age);
                    break;
                case Pharmacist:
                    staff = new Pharmacist(id, name, role, gender, age);
                    break;
                case Administrator:
                    staff = new Administrator(id, name, role, gender, age);
                    break;
                default:
                    throw new IOException("Invalid line: expected " + line[2] + " to be one of 'Doctor', 'Pharmacist', 'Administrator'.");
            }

            staffs.add(staff);
        }

        return staffs;
    }
    
    public static ArrayList<Patient> readPatientListFile(String path) throws IOException {
        String[][] values = ReadFile.readCSV(path);

        ArrayList<Patient> patients = new ArrayList<Patient>();
        for (int i = 0; i < values.length; i++) {
            String[] line = values[i];
            if (line.length != 6) {
                String line_full = String.join(",", line);
                throw new IOException("Invalid line " + line_full + ": expected 6 elements.");
            }
            String name = line[1], birthDate = line[2], gender = line[3], bloodType = line[4], contactInfo = line[5];
            UserId id;
            try {
                id = new UserId(line[0]);
            } catch (IllegalArgumentException e) {
                throw new IOException("Invalid line: expected " + line[0] + "to be a user ID.");
            }

            patients.add(new Patient(id, name, birthDate, gender, bloodType, contactInfo));
        }

        return patients;
    }

    public static ArrayList<Medicine> readMedicineListFile(String path) throws IOException {
        String[][] values = ReadFile.readCSV(path);

        ArrayList<Medicine> inventory = new ArrayList<Medicine>();
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

            inventory.add(new Medicine(name, initialStock, lowStockLevelAlert));
        }

        return inventory;
    }

    public static ArrayList<ReplenishmentRequest> readRequestListFile(String path) throws IOException {
        String[][] values = ReadFile.readCSV(path);

        ArrayList<ReplenishmentRequest> requests = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            String[] line = values[i];
            requests.add(new ReplenishmentRequest(line));
        }
        return requests;
    }

    public static ArrayList<Appointment> readAppointmentListFile(String path) throws IOException {
        String[][] values = ReadFile.readCSV(path);

        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        for (int i = 0; i < values.length; i++) {
            String[] line = values[i];
            appointments.add(new Appointment(line));
        }

        return appointments;
    }
}

