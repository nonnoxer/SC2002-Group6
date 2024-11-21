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

/**
 * The UserDatabase class manages the user accounts, staff, and patient records in the system.
 * It handles the reading and writing of user data from/to CSV files and provides functionality to manage 
 * accounts for various roles such as Doctor, Administrator, Pharmacist, and Patient.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class UserDatabase implements UserDatabaseApiPatient, UserDatabaseApiAdministrator, UserDatabaseApiDoctor {
    private HashMap<UserId, UserAccount> accounts;
    private HashMap<UserId, Patient> patients;
    private HashMap<UserId, Staff> staffs;
    private HashMap<UserId, DoctorApiPatient> doctors;
    private AppointmentDatabase appointmentDb;
    private Inventory inventory;

    private String accountListPath, staffListPath, patientListPath;

    /**
     * Constructs a UserDatabase by reading user data from the provided file paths.
     * If the account list file does not exist, it creates a new one.
     *
     * @param accountListPath the path to the account list CSV file
     * @param patientListPath the path to the patient list CSV file
     * @param staffListPath the path to the staff list CSV file
     * @throws IOException if there is an error reading the files
     */
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

    /**
     * Converts a given name to a username format (lowercase, spaces replaced with underscores).
     * 
     * @param name the name to convert
     * @return the converted username
     */
    private String nameToUsername(String name) {
        return name.toLowerCase().replace(" ", "_");
    }

    /**
     * Initializes users, linking them with their appointments and inventory. This should be called after the 
     * UserDatabase is constructed and when appointments and inventory are available.
     *
     * @param inventory the inventory object
     * @param appointmentDb the appointment database
     */
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

    /**
     * Updates the account file with the current list of user accounts.
     */
    private void updateAccountFile() {
        try {
            WriteFile.writeFile(accounts.values(), accountListPath);
        } catch (Exception e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    /**
     * Updates the staff file with the current list of staff members.
     */
    private void updateStaffFile() {
        try {
            WriteFile.writeFile(staffs.values(), staffListPath);
        } catch (Exception e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }

    /**
     * Updates the patient file with the current list of patients.
     */
    private void updatePatientFile(){
        try {
            WriteFile.writeFile(patients.values(), patientListPath);
        } catch (Exception e) {
            System.out.println("Error updating file: " + e.getMessage());
        }
    }


    /**
     * Updates the patient by calling the private function.
     */
    public void updatePatient(){
        updatePatientFile();
    }

    /**
     * Retrieves a UserAccount based on the provided username.
     *
     * @param username the username of the account to retrieve
     * @return the UserAccount with the specified username, or null if no account is found
     */
    public UserAccount getAccount(String username) {
        for (UserAccount account : accounts.values()) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Registers a new patient and creates their associated user account.
     * 
     * @param username the username for the new patient
     * @param name the full name of the new patient
     * @param birthDate the birth date of the new patient
     * @param gender the gender of the new patient
     * @param bloodType the blood type of the new patient
     * @param contactInfo the contact information for the new patient
     * @return the newly created Patient object
     */
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

    /**
     * Retrieves a list of patients associated with a given list of appointments.
     *
     * @param appointments the list of appointments for which to find associated patients
     * @return a list of patients corresponding to the provided appointments
     */
    public ArrayList<Patient> getPatients(ArrayList<Appointment> appointments) {
        ArrayList<Patient> result = new ArrayList<>();
        for (Appointment appointment : appointments) {
            Patient patient = patients.get(appointment.getPatientId());
            if (patient != null) result.add(patient);
        }
        return result;
    }

    /**
     * Finds and returns a patient by their unique user ID.
     *
     * @param id the unique ID of the patient to find
     * @return the Patient associated with the provided ID, or null if not found
     */
    public Patient findPatientId(UserId id) {
        return patients.get(id);
    }

    /**
     * Finds and returns a staff member by their unique user ID.
     *
     * @param id the unique ID of the staff member to find
     * @return the Staff associated with the provided ID, or null if not found
     */
    public Staff findStaffId(UserId id) {
        return staffs.get(id);
    }

    /**
     * Retrieves a list of doctors that provide patient care (DoctorApiPatient).
     *
     * @return a list of doctors (DoctorApiPatient)
     */
    public ArrayList<DoctorApiPatient> getDoctors() {
        return new ArrayList<DoctorApiPatient>(this.doctors.values());
    }

    /**
     * Finds and returns a doctor by their unique user ID.
     *
     * @param doctorId the unique ID of the doctor to find
     * @return the DoctorApiPatient associated with the provided ID, or null if not found
     */
    public DoctorApiPatient findDoctorId(UserId doctorId) {
        return doctors.get(doctorId);
    }

    /**
     * Retrieves a list of all staff members.
     *
     * @return a list of all staff members
     */
    public ArrayList<Staff> getStaff() {
        return new ArrayList<>(this.staffs.values());
    }

    /**
     * Adds a new staff member of the specified role (Doctor, Pharmacist, Administrator).
     *
     * @param name the name of the new staff member
     * @param role the role of the new staff member (Doctor, Pharmacist, Administrator)
     * @param gender the gender of the new staff member
     * @param age the age of the new staff member
     * @return the newly created Staff object, or null if an invalid role is specified
     */
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

    /**
     * Updates the details of an existing staff member.
     * 
     * @param id the unique ID of the staff member to update
     * @param name the new name for the staff member
     * @param role the new role for the staff member (Note: role change is not allowed in the current implementation)
     * @param gender the new gender for the staff member
     * @param age the new age for the staff member
     * @return the updated Staff object, or null if the staff member does not exist
     */
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

    /**
     * Removes a staff member based on their unique user ID.
     *
     * @param id the unique ID of the staff member to remove
     * @return the removed Staff object, or null if the staff member does not exist
     */
    public Staff removeStaff(UserId id) {
        Staff staff = staffs.remove(id);
        accounts.remove(id);

        updateAccountFile();
        updateStaffFile();

        return staff;
    }

    /**
     * Sets the password for a user account based on their unique user ID.
     *
     * @param id the unique ID of the user to set the password for
     * @param password the new password to set
     */
    public void setPassword(UserId id, String password) {
        UserAccount account = accounts.get(id);
        account.setPassword(password);
        updateAccountFile();
    }
}
