package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import user.Patient;
import user.Staff;
import user.User;
import user.UserAccount;

public class UserDatabase {
    private UserAccount[] accounts;

    public UserDatabase(String path, Patient[] patients, Staff[] staffs) throws IOException {
        if (Files.notExists(Path.of(path))) {
            System.out.printf("%s does not exist, creating new database...\n\n", path);
            accounts = new UserAccount[patients.length + staffs.length];
            int i = 0;
            for (Patient patient : patients) {
                String username = patient.getName().toLowerCase().replace(" ", "_");
                accounts[i] = new UserAccount(patient.getID(), username, "", "Patient");
                i++;
            }
            for (Staff staff : staffs) {
                String username = staff.getName().toLowerCase().replace(" ", "_");
                accounts[i] = new UserAccount(staff.getID(), username, "", staff.getRole());
                i++;
            }
            WriteFile.writeFile(accounts, path);
        } else {
            accounts = ReadFile.readAccountListFile(path);
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

    public Patient findPatientId(Patient[] patients, String id) {
        for (Patient patient : patients) {
            if (patient.getID().equals(id)) return patient;
        }
        return null;
    }

    public Staff findStaffId(Staff[] staffs, String id) {
        for (Staff staff : staffs) {
            if (staff.getID().equals(id)) return staff;
        }
        return null;
    }
}
