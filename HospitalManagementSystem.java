import java.io.IOException;
import java.util.ArrayList;

import data.ReadFile;
import data.UserDatabase;
import medicine.Inventory;
import medicine.Medicine;
import menus.UserInterface;
import user.Administrator;
import user.Doctor;
import user.DoctorApi;
import user.Patient;
import user.Pharmacist;
import user.Staff;

public class HospitalManagementSystem {
    private ArrayList<Staff> staffs;
    private ArrayList<Patient> patients;
    private ArrayList<Medicine> medicines;
    private Inventory inventory;
    private UserDatabase db;
    private UserInterface ui;

    public HospitalManagementSystem(String staffListPath, String patientListPath, String medicineListPath, String accountListPath) {
        try {
            staffs = ReadFile.readStaffListFile(staffListPath);
            patients = ReadFile.readPatientListFile(patientListPath);
            medicines = ReadFile.readMedicineListFile(medicineListPath);
            db = new UserDatabase(accountListPath, patients, staffs);
        } catch (IOException e) {
            System.out.println(e);
        }

        inventory = new Inventory(medicines);

        initUsers();

        ui = new UserInterface(db);
    }

    private void initUsers() {
        // Patients have access to specific functions of Doctor
        ArrayList<DoctorApi> doctorApis = new ArrayList<DoctorApi>();
        for (Staff staff : staffs) {
            switch (staff.getRole()) {
                case "Doctor": {
                    Doctor temp = (Doctor) staff;
                    doctorApis.add(new DoctorApi(temp));
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
                    temp.init(staffs, inventory);
                    break;
                }
                default:
                    break;
            }
        }

        // Set available doctors for each patient
        for (Patient patient : patients) {
            patient.setDoctors(doctorApis);
        }
    }

    public void start() {
        ui.start();
    }
}
