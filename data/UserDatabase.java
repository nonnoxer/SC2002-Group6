package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import user.Patient;
import user.Staff;
import user.UserAccount;

public class UserDatabase {
    private ArrayList<UserAccount> accounts;
    private ArrayList<Patient> patients;
    private ArrayList<Staff> staffs;

    public UserDatabase(String path, ArrayList<Patient> patients, ArrayList<Staff> staffs) throws IOException {
        if (Files.notExists(Path.of(path))) {
            System.out.printf("%s does not exist, creating new database...\n\n", path);
            accounts = new ArrayList<UserAccount>();
            for (Patient patient : patients) {
                String username = patient.getName().toLowerCase().replace(" ", "_");
                accounts.add(new UserAccount(patient.getID(), username, "", "Patient"));
            }
            for (Staff staff : staffs) {
                String username = staff.getName().toLowerCase().replace(" ", "_");
                accounts.add(new UserAccount(staff.getID(), username, "", staff.getRole()));
            }
            WriteFile.writeFile(accounts, path);
        } else {
            accounts = ReadFile.readAccountListFile(path);
        }

        this.patients = patients;
        this.staffs = staffs;
    }

    public UserAccount getAccount(String username) {
        for (UserAccount account : accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public Patient findPatientId(String id) {
        for (Patient patient : patients) {
            if (patient.getID().equals(id)) return patient;
        }
        return null;
    }

    public Staff findStaffId(String id) {
        for (Staff staff : staffs) {
            if (staff.getID().equals(id)) return staff;
        }
        return null;
    }
}
