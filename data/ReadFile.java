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

/**
 * Utility class for reading data from CSV files and converting them into corresponding objects.
 * This class handles the reading and parsing of CSV files to create lists of objects for users,
 * staff, patients, appointments, medicines, and replenishment requests.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class ReadFile {
    /**
     * Reads a CSV file from the specified path and returns a 2D array where each row represents a line of the CSV,
     * and each element in the row represents a field in that line.
     *
     * @param path the path to the CSV file
     * @return a 2D array containing the CSV data
     * @throws IOException if there is an error reading the file
     */
    private static String[][] readCSV(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        String[][] lines_separated = new String[lines.size()][];
        for (int i = 0; i < lines_separated.length; i++) {
            lines_separated[i] = lines.get(i).split(",", -1);
        }
        return lines_separated;
    }

    /**
     * Reads a CSV file containing user account information and returns a list of UserAccount objects.
     * 
     * @param path the path to the CSV file
     * @return a list of UserAccount objects
     * @throws IOException if there is an error reading or parsing the file
     */
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
    
    /**
     * Reads a CSV file containing staff information and returns a list of Staff objects.
     * 
     * @param path the path to the CSV file
     * @return a list of Staff objects
     * @throws IOException if there is an error reading or parsing the file
     */
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
    
    /**
     * Reads a CSV file containing patient information and returns a list of Patient objects.
     * 
     * @param path the path to the CSV file
     * @return a list of Patient objects
     * @throws IOException if there is an error reading or parsing the file
     */
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

    /**
     * Reads a CSV file containing medicine information and returns a list of Medicine objects.
     * 
     * @param path the path to the CSV file
     * @return a list of Medicine objects
     * @throws IOException if there is an error reading or parsing the file
     */
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

    /**
     * Reads a CSV file containing replenishment request information and returns a list of ReplenishmentRequest objects.
     * 
     * @param path the path to the CSV file
     * @return a list of ReplenishmentRequest objects
     * @throws IOException if there is an error reading or parsing the file
     */
    public static ArrayList<ReplenishmentRequest> readRequestListFile(String path) throws IOException {
        String[][] values = ReadFile.readCSV(path);

        ArrayList<ReplenishmentRequest> requests = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            String[] line = values[i];
            requests.add(new ReplenishmentRequest(line));
        }
        return requests;
    }

    /**
     * Reads a CSV file containing appointment information and returns a list of Appointment objects.
     * 
     * @param path the path to the CSV file
     * @return a list of Appointment objects
     * @throws IOException if there is an error reading or parsing the file
     */
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

