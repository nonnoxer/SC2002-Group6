import java.io.IOException;

import data.ReadFile;
import medicine.Medicine;
import user.Patient;
import user.Staff;

public class HospitalManagementSystem {
    private Staff[] staffs;
    private Patient[] patients;
    private Medicine[] inventory;

    public HospitalManagementSystem(String staffListPath, String patientListPath, String medicineListPath) {
        try {
            staffs = ReadFile.readStaffListFile(staffListPath);
            patients = ReadFile.readPatientListFile(patientListPath);
            inventory = ReadFile.readMedicineListFile(medicineListPath);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
