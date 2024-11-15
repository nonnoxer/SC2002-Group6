import java.io.IOException;
import java.util.ArrayList;

import appointment.AppointmentSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;

import data.ReadFile;
import data.UserDatabase;
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
    private UserInterface ui;

    public HospitalManagementSystem(String staffListPath, String patientListPath, String medicineListPath, String accountListPath) {
        try {
            staffs = ReadFile.readStaffListFile(staffListPath);
            patients = ReadFile.readPatientListFile(patientListPath);
            db = new UserDatabase(accountListPath, patients, staffs);
            inventory = new Inventory(medicineListPath);
        } catch (IOException e) {
            System.out.println(e);
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
                    // Create doctor API
                    doctorApis.add(new DoctorApi(temp));

                    // Set appointment slots
                    ArrayList<AppointmentSlot> slots = generateSlots(5);
                    temp.setAppointmentSlots(slots);
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

    // Generate appointment slots at 30 minute intervals from 8.00 am to 1.00 pm, and from 2.00 pm to 4.30 pm
    private ArrayList<AppointmentSlot> generateSlots(int numDays) {
        ArrayList<AppointmentSlot> slots = new ArrayList<AppointmentSlot>();
        LocalDate startDay = LocalDate.now();
        for (int i = 0; i < numDays; i++) {
            LocalDate curDay = startDay.plusDays(i);
            for (int j = 0; j < 17; j++) {
                int hour = j / 2 + 8;
                int minute = j % 2 * 30;
                if (hour == 13) continue;
                LocalDateTime time = curDay.atTime(hour, minute);
                slots.add(new AppointmentSlot(time));
            }
        }
        return slots;
    }

    public void start() {
        ui.start();
    }
}
