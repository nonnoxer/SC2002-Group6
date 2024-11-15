package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import appointment.Appointment;
import appointment.AppointmentSlot;
import medicine.Medicine;
import record.AppointmentOutcomeRecord;
import user.Administrator;
import user.Doctor;
import user.Patient;
import user.Pharmacist;
import user.Staff;
import user.UserAccount;

public class ReadFile {
    private static String[][] readCSV(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        String[][] lines_separated = new String[lines.size()][];
        for (int i = 0; i < lines_separated.length; i++) {
            lines_separated[i] = lines.get(i).split(",");
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
            String id = line[0], username = line[1], password = line[2], role = line[3];

            if (!(role.equals("Doctor") || role.equals("Pharmacist") || role.equals("Administrator") || role.equals("Patient"))) {
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
            String id = line[0], name = line[1], birthDate = line[2], gender = line[3], bloodType = line[4], contactInfo = line[5];

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

    public static ArrayList<Appointment> readAppointmentListFile(String path) throws IOException {
        String[][] values = ReadFile.readCSV(path);

        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        for (int i = 0; i < values.length; i++) {
            String[] line = values[i];
            if (line.length != 9) {
                String line_full = String.join(",", line);
                throw new IOException("Invalid line " + line_full + ": expected 3 elements.");
            }

            String patientId = line[0];
            String doctorId = line[1];
            String appointmentStatus = line[2];
            String availability = line[3];
            String dateString = line[4];
            String serviceType = line[5];
            String consultationNotes = line[6];
            String prescriptionStatus = line[7];
            String medicines = line[8];

            LocalDateTime date;
            try {
                date = LocalDateTime.parse(dateString);
            } catch (DateTimeParseException e) {
                throw new IOException("Invalid line: expected " + line[4] + " to be a date-time string.");
            }
            AppointmentSlot slot = new AppointmentSlot(date);
            if (availability.equals("false")) slot.schedule();

            Appointment appointment = new Appointment(patientId, doctorId, appointmentStatus, slot);

            if (!serviceType.equals("")) {
                String[] medicineNames = medicines.split("::");
                ArrayList<Medicine> prescription = new ArrayList<>();
                for (String medicineName : medicineNames) {
                    prescription.add(new Medicine(medicineName, 0, 0));
                }
                AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(slot, serviceType, consultationNotes, prescriptionStatus, prescription);
                appointment.complete(record);
            }

            appointments.add(appointment);
        }

        return appointments;
    }

    private int parseElement(String[] line, int index) throws IOException {
        int result;
        try {
            result = Integer.parseInt(line[index]);
        } catch (NumberFormatException e) {
            throw new IOException("Invalid line: expected " + line[index] + " to be an integer.");
        }
        return result;
    }
}

