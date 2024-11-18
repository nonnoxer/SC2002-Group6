import java.io.IOException;
import java.util.ArrayList;

import appointment.Appointment;
import appointment.AppointmentDatabase;
import appointment.AppointmentSlot;
import appointment.Schedule;

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
            System.out.println(e);
        }

        initUsers();
        initAppointmentSlots();

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

    private void initAppointmentSlots() {
        ArrayList<Appointment> appointments = appointmentDb.getAppointments();

        for (Appointment appointment : appointments) {
            // Add to patient
            for (Patient patient: patients) {
                if (patient.getID().equals(appointment.getPatientId())) {
                    patient.getAppointments().add(appointment);
                }
            }
            
            // Add to doctor
            for (Staff staff : staffs) {
                if (!staff.getRole().equals("Doctor")) continue;
                Doctor temp = (Doctor) staff;
                if (temp.getID().equals(appointment.getDoctorId())) {
                    temp.getAppointments().add(appointment);

                    // Update schedule
                    Schedule schedule = temp.getPersonalSchedule();
                    LocalDateTime dateTime = appointment.getSlot().getDate();
                    ArrayList<AppointmentSlot> slots = schedule.getSlots(dateTime.toLocalDate());
                    for (int i = 0; i < slots.size(); i++) {
                        if (slots.get(i).getDate().equals(dateTime)) {
                            slots.set(i, appointment.getSlot());
                        }
                    }
                }
            }
        }
    }

    public void start() {
        ui.start();
    }
}
