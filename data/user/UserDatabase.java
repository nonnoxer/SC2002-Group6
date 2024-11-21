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
import user.Role;
import user.Staff;
import user.UserAccount;
import user.UserId;

public class UserDatabase implements UserDatabaseApiPatient, UserDatabaseApiAdministrator, UserDatabaseApiDoctor {
    private HashMap<UserId, UserAccount> accounts;
    private HashMap<UserId, Patient> patients;
    private HashMap<UserId, Staff> staffs;
    private HashMap<UserId, DoctorApiPatient> doctors;
    private AppointmentDatabase appointmentDb;
    private Inventory inventory;

    private String accountListPath, staffListPath, patientListPath;

    public UserDatabase(String accountListPath, String patientListPath, String staffListPath) throws IOException {
        ArrayList<Staff> staffList = ReadFile.readStaffListFile(staffListPath);
        ArrayList<Patient> patientList = ReadFile.readPatientListFile(patientListPath);

        this.accounts = new HashMap<>();
        this.staffs = new HashMap<>();
        this.patients = new HashMap<>();
        this.doctors = new HashMap<>();
        this.appointmentDb = null;
        this.inventory = null;

        for (Patient patient: patientList) {
            patients.put(patient.getId(), patient);
        }
        for (Staff staff : staffList) {
            staffs.put(staff.getId(), staff);
        }

        if (Files.notExists(Path.of(accountListPath))) {
            System.out.printf("%s does not exist, creating new database...\n\n", accountListPath);
            accounts = new HashMap<>();
            for (Patient patient : patientList) {
                String username = nameToUsername(patient.getName());
                accounts.put(patient.getId(), new UserAccount(patient.getId(), username, "", Role.Patient));
            }
            for (Staff staff : staffList) {
                String username = nameToUsername(staff.getName());
                accounts.put(staff.getId(), new UserAccount(staff.getId(), username, "", staff.getRole()));
            }
            WriteFile.writeFile(accounts.values(), accountListPath);
        } else {
            ArrayList<UserAccount> accountList = ReadFile.readAccountListFile(accountListPath);
            for (UserAccount account : accountList) {
                accounts.put(account.getId(), account);
            }
        }

        this.accountListPath = accountListPath;
        this.staffListPath = staffListPath;
        this.patientListPath = patientListPath;
    }

    private String nameToUsername(String name) {
        return name.toLowerCase().replace(" ", "_");
    }

    public void initUsers(Inventory inventory, AppointmentDatabase appointmentDb) {
        this.appointmentDb = appointmentDb;
        this.inventory = inventory;

        // Patients have access to specific functions of Doctor
        for (Staff staff : staffs.values()) {
            switch (staff.getRole()) {
                case Doctor: {
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
                case Pharmacist: {
                    Pharmacist temp = (Pharmacist) staff;
                    temp.init(appointmentDb, inventory);
                    break;
                }
                // Set global inventory and staff list for each administrator
                case Administrator: {
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
            WriteFile.writeFile(accounts.values(), accountListPath);
        } catch (Exception e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    private void updateStaffFile() {
        try {
            WriteFile.writeFile(staffs.values(), staffListPath);
        } catch (Exception e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    private void updatePatientFile(){
        try {
            WriteFile.writeFile(patients.values(), patientListPath);
        } catch (Exception e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    public void updatePatient(){
        updatePatientFile();
    }

    public UserAccount getAccount(String username) {
        for (UserAccount account : accounts.values()) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public Patient registerPatient(String username, String name, String birthDate, String gender, String bloodType, String contactInfo) {
        int num = 0;
        for (UserId patientId : patients.keySet()) {
            if (patientId.getNum() >= num) num = patientId.getNum() + 1;
        }
        UserId newId = new UserId('P', num);
        
        Patient patient = new Patient(newId, name, birthDate, gender, bloodType, contactInfo);
        patients.put(newId, patient);
        UserAccount account = new UserAccount(newId, username, contactInfo, Role.Patient);
        accounts.put(newId, account);

        updatePatientFile();
        updateAccountFile();

        patient.init(this, appointmentDb);

        return patient;
    }

    public ArrayList<Patient> getPatients(ArrayList<Appointment> appointments) {
        ArrayList<Patient> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            Patient patient = patients.get(appointment.getPatientId());
            if (patient != null) result.add(patient);
        }
        return result;
    }

    public Patient findPatientId(UserId id) {
        return patients.get(id);
    }

    public Staff findStaffId(UserId id) {
        return staffs.get(id);
    }

    public ArrayList<DoctorApiPatient> getDoctors() {
        return new ArrayList<DoctorApiPatient>(this.doctors.values());
    }

    public DoctorApiPatient findDoctorId(UserId doctorId) {
        return doctors.get(doctorId);
    }

    public ArrayList<Staff> getStaff() {
        return new ArrayList<>(this.staffs.values());
    }

    public Staff addStaff(String name, Role role, String gender, int age) {
        char prefix;
        switch (role) {
            case Doctor:
                prefix = 'D';
                break;
            case Administrator:
                prefix = 'A';
                break;
            case Pharmacist:
                prefix = 'P';
                break;
            default:
                return null;
        }
        int num = 0;
        for (UserId staffId : staffs.keySet()) {
            if (staffId.getPrefix() == prefix && staffId.getNum() >= num) num = staffId.getNum() + 1;
        }
        UserId newId = new UserId(prefix, num);

        Staff staff;
        UserAccount newAccount = new UserAccount(newId, nameToUsername(name), "", role);
    
        switch (role) {
            case Doctor: {
                Doctor temp = new Doctor(newId, name, role, gender, age);
                temp.init(this, inventory);
                temp.setSchedule(LocalDate.parse("2024-01-01"), LocalDate.parse("2025-12-31"));
                temp.setAppointmentDb(appointmentDb);
                staff = temp;
                break;
            }
            case Pharmacist: {
                Pharmacist temp = new Pharmacist(newId, name, role, gender, age);
                temp.init(appointmentDb, inventory);
                staff = temp;
                break;
            }
            case Administrator: {
                Administrator temp = new Administrator(newId, name, role, gender, age);
                temp.init(this, appointmentDb, inventory);
                staff = temp;
                break;
            }
            default:
                return null;
        }

        staffs.put(newId, staff);
        accounts.put(newId, newAccount);

        updateStaffFile();
        updateAccountFile();

        return staff;
    }

    public Staff updateStaff(UserId id, String name, Role role, String gender, int age) {
        Staff staff = staffs.get(id);
        if (staff == null) return null;
        staff.setName(name);
        // staff.setRole(name); I don't think you should be allowed to change staff role
        staff.setGender(gender);
        staff.setAge(age);

        updateStaffFile();

        return staff;
    }

    public Staff removeStaff(UserId id) {
        Staff staff = staffs.remove(id);
        accounts.remove(id);

        updateAccountFile();
        updateStaffFile();

        return staff;
    }

    public void setPassword(UserId id, String password) {
        UserAccount account = accounts.get(id);
        account.setPassword(password);
        updateAccountFile();
    }
}
