package data.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import appointment.Appointment;
import data.ReadFile;
import data.WriteFile;
import data.appointment.AppointmentDatabase;
import medicine.Inventory;
import user.Administrator;
import user.Doctor;
import user.DoctorApiPatient;
import user.Patient;
import user.Pharmacist;
import user.Staff;
import user.UserAccount;

public class UserDatabase implements UserDatabaseApiPatient, UserDatabaseApiAdministrator, UserDatabaseApiDoctor {
    private ArrayList<UserAccount> accounts;
    private HashMap<String, Patient> patients;
    private HashMap<String, Staff> staffs;
    private HashMap<String, DoctorApiPatient> doctors;

    private String accountListPath, staffListPath;

    public UserDatabase(String accountListPath, String patientListPath, String staffListPath) throws IOException {
        ArrayList<Staff> staffList = ReadFile.readStaffListFile(staffListPath);
        ArrayList<Patient> patientList = ReadFile.readPatientListFile(patientListPath);

        this.staffs = new HashMap<>();
        this.patients = new HashMap<>();
        this.doctors = new HashMap<>();

        for (Patient patient: patientList) {
            patients.put(patient.getId(), patient);
        }
        for (Staff staff : staffList) {
            staffs.put(staff.getId(), staff);
        }

        if (Files.notExists(Path.of(accountListPath))) {
            System.out.printf("%s does not exist, creating new database...\n\n", accountListPath);
            accounts = new ArrayList<UserAccount>();
            for (Patient patient : patientList) {
                String username = nameToUsername(patient.getName());
                accounts.add(new UserAccount(patient.getId(), username, "", "Patient"));
            }
            for (Staff staff : staffList) {
                String username = nameToUsername(staff.getName());
                accounts.add(new UserAccount(staff.getId(), username, "", staff.getRole()));
            }
            WriteFile.writeFile(accounts, accountListPath);
        } else {
            accounts = ReadFile.readAccountListFile(accountListPath);
        }

        this.accountListPath = accountListPath;
        this.staffListPath = staffListPath;
    }

    private String nameToUsername(String name) {
        return name.toLowerCase().replace(" ", "_");
    }

    public void initUsers(Inventory inventory, AppointmentDatabase appointmentDb) {
        // Patients have access to specific functions of Doctor
        for (Staff staff : staffs.values()) {
            switch (staff.getRole()) {
                case "Doctor": {
                    Doctor temp = (Doctor) staff;
                    temp.init(this, inventory);
                    // Create doctor API
                    doctors.put(temp.getId(), temp);

                    // Set appointment slots
                    temp.setSchedule(LocalDate.parse("2024-01-01"), LocalDate.parse("2025-12-31"));
                    temp.setAppointmentDb(appointmentDb);
                    break;
                }
                // Set global inventory for each pharmacist
                case "Pharmacist": {
                    Pharmacist temp = (Pharmacist) staff;
                    temp.init(inventory);
                    break;
                }
                // Set global inventory and staff list for each administrator
                case "Administrator": {
                    Administrator temp = (Administrator) staff;
                    temp.init(this, appointmentDb, inventory);
                    break;
                }
                default:
                    break;
            }
        }

        for (Patient patient : patients.values()) {
            patient.init(this, appointmentDb);
        }
    }

    private void updateAccountFile() {
        try {
            WriteFile.writeFile(accounts, accountListPath);
            System.out.println("Account list updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    private void updateStaffFile() {
        try {
            WriteFile.writeFile(staffs.values(), staffListPath);
            System.out.println("Staff list updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    public UserAccount getAccount(String username) {
        for (UserAccount account : accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public ArrayList<Patient> getPatients(ArrayList<Appointment> appointments) {
        ArrayList<Patient> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            Patient patient = patients.get(appointment.getPatientId());
            if (patient != null) result.add(patient);
        }
        return result;
    }

    public Patient findPatientId(String id) {
        return patients.get(id);
    }

    public Staff findStaffId(String id) {
        return staffs.get(id);
    }

    public ArrayList<DoctorApiPatient> getDoctors() {
        return new ArrayList<DoctorApiPatient>(this.doctors.values());
    }

    public DoctorApiPatient findDoctorId(String doctorId) {
        return doctors.get(doctorId);
    }

    public ArrayList<Staff> getStaff() {
        return new ArrayList<>(this.staffs.values());
    }

    public Staff addStaff(String id, String name, String role, String gender, int age) {
        Staff staff = new Staff(id, name, role, gender, age);
        UserAccount newAccount = new UserAccount(id, nameToUsername(name), "", role);

        staffs.put(id, staff);
        accounts.add(newAccount);

        updateStaffFile();
        updateAccountFile();

        return staff;
    }

    public Staff updateStaff(String id, String name, String role, String gender, int age) {
        Staff staff = staffs.get(id);
        if (staff == null) return null;
        staff.setName(name);
        staff.setRole(name);
        staff.setGender(gender);
        staff.setAge(age);

        updateStaffFile();

        return staff;
    }

    public Staff removeStaff(String id) {
        Staff staff = staffs.remove(id);
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getId().equals(id)) {
                accounts.remove(i);
                break;
            }
        }

        updateAccountFile();
        updateStaffFile();

        return staff;
    }
}
