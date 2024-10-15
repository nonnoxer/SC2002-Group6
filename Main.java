import java.io.IOException;

import data.LogIn;
import data.LogInController;
import data.ReadFile;
import data.WriteFile;
import medicine.Medicine;
import user.Patient;
import user.Staff;

import menus.LoginMenu;

class Main {
    public static void main(String[] args) {
        // try {
        //     Staff[] staffs = ReadFile.readStaffListFile("Staff_List.csv");
        //     for (Staff staff : staffs) {
        //         System.out.println(staff.getName());
        //     }

        //     Patient[] patients = ReadFile.readPatientListFile("Patient_List.csv");
        //     Medicine[] inventory = ReadFile.readMedicineListFile("Medicine_List.csv");

        //     WriteFile.writeFile(staffs, "Staff_out.csv");
        //     WriteFile.writeFile(patients, "Patient_out.csv");
        //     WriteFile.writeFile(inventory, "Medicine_out.csv");
        // } catch (IOException e) {
        //     System.out.println(e);
        // }

        LoginMenu.showLoginMenu();
    }
}