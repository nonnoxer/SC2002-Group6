import java.io.IOException;

import data.ReadFile;
import medicine.Medicine;
import user.Patient;
import user.Staff;

class Main {
    public static void main(String[] args) {
        try {
            Staff[] staffs = ReadFile.readStaffListFile("Staff_List.csv");
            for (Staff staff : staffs) {
                System.out.println(staff.getName());
            }

            Patient[] patients = ReadFile.readPatientListFile("Patient_List.csv");
            Medicine[] inventory = ReadFile.readMedicineListFile("Medicine_List.csv");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}