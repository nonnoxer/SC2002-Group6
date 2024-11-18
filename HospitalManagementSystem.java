import java.io.IOException;
import java.util.ArrayList;

import java.time.LocalDate;

import data.ReadFile;
import data.UserDatabase;
import data.appointment.AppointmentDatabase;
import medicine.Inventory;
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
    private Inventory inventory;
    private UserDatabase db;
    private AppointmentDatabase appointmentDb;
    private UserInterface ui;

    public HospitalManagementSystem(String staffListPath, String patientListPath, String medicineListPath, String accountListPath, String appointmentListPath) {
        try {
            staffs = ReadFile.readStaffListFile(staffListPath);
            patients = ReadFile.readPatientListFile(patientListPath);
            db = new UserDatabase(accountListPath, patients, staffs);
            inventory = new Inventory(medicineListPath);
            appointmentDb = new AppointmentDatabase(appointmentListPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                    temp.init(inventory);
                    // Create doctor API
                    doctorApis.add(new DoctorApi(temp));

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
            patient.setAppointmentDb(appointmentDb);
        }
    }

    public void start() {
        ui.start();
    }
}
