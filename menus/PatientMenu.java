//auth: amu
package menus;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;

import appointment.Appointment;
import appointment.AppointmentSlot;
import record.AppointmentOutcomeRecord;
import record.MedicalRecord;
import user.DoctorApi;
import user.Patient;


public class PatientMenu extends Menu{
    private Scanner sc;
    private Patient patient;

    public PatientMenu(Scanner sc, Patient patient) {
        this.sc = sc;
        this.patient = patient;
    }

    //overiding, implemented from menu class
    @Override
    public void showMenu(){
        int choice = -1;
        
        while (choice != 9) {
            System.out.println("\n===== Patient Menu =====");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            handleSelection(choice);
        }
    }

    private void handleSelection(int choice){
        switch (choice) {
            case 1:
                viewMedicalRecord();
                break;
            case 2:
                updatePersonalInfo();
                break;
            case 3:
                viewAvailableSlots();
                break;
            case 4:
                scheduleAppointment();
                break;
            case 5:
                rescheduleAppointment();
                break;
            case 6:
                cancelAppointment();
                break;
            case 7:
                viewScheduledAppointments();
                break;
            case 8:
                viewPastAppointments();
                break;
            case 9:
                break;
            default:
                System.out.println("The option is chosen incorrectly, please try again!");
        }
    }

    private void viewMedicalRecord() {
        MedicalRecord record = this.patient.getMedicalRecord();

        System.out.println("\nMedical Record");
        System.out.printf("\tPatient ID: %s\n", record.getId());
        System.out.printf("\tName: %s\n", record.getName());
        System.out.printf("\tDate of Birth: %s\n", record.getBirthDate());
        System.out.printf("\tGender: %s\n", record.getGender());
        System.out.printf("\tContact Information: %s\n", record.getContactInfo());
        System.out.printf("\tBlood Type: %s\n", record.getBloodType());

        System.out.println("\tPast Diagnoses and Treatments:");
        ArrayList<AppointmentOutcomeRecord> outcomes = record.getPastAppointments();
        for (AppointmentOutcomeRecord outcome : outcomes) {
            System.out.printf("\t- %s\n", outcome.getSlot().getDate());
            System.out.printf("\t  Notes: %s\n", outcome.getConsultationNotes());
            // TODO: update
            System.out.printf("\t  Prescription: %s\n", outcome.getPrescriptionStatus());
        }
        if (outcomes.size() == 0) System.out.println("No past appointment records.");

        System.out.println("\nContinue... [enter]");
        sc.nextLine();
        sc.nextLine();
    }

    private void updatePersonalInfo() {
        MedicalRecord record = this.patient.getMedicalRecord();

        
    }

    private DoctorApi getDoctor() {
        ArrayList<DoctorApi> doctorApis = this.patient.getDoctors();
        System.out.println("Doctors:");
        for (int i = 0; i < doctorApis.size(); i++) {
            System.out.printf("%d. %s\n", i, doctorApis.get(i).getName());
        }
        if (doctorApis.size() == 0) {
            System.out.println("No available doctors.");
            return null;
        }

        System.out.print("Choose a doctor: ");
        int choice = sc.nextInt();
        if (choice < 0 || choice >= doctorApis.size()) {
            System.out.println("Invalid choice.");
            return null;
        }

        return doctorApis.get(choice);
    }

    private void viewAvailableSlots() {
        DoctorApi doctor = getDoctor();
        ArrayList<AppointmentSlot> slots = doctor.getPersonalSchedule();

        System.out.println("Available Slots: ");
        int numSlots = 0;
        for (AppointmentSlot slot : slots) {
            if (slot.getAvailability()) {
                System.out.printf("%s\n", slot.getDate().toString());
                numSlots++;
            }
        }
        if (numSlots == 0) {
            System.out.println("No available slots.");
        }
    }

    private void scheduleAppointment() {
        DoctorApi doctor = getDoctor();
        AppointmentSlot slot = new AppointmentSlot(LocalDateTime.now());

        this.patient.scheduleAppointment(doctor.getId(), slot);
    }

    private void rescheduleAppointment() {
        ArrayList<Appointment> appointments = this.patient.getAppointments();
        int i = 0;
        System.out.println("Choose an appointment to reschedule:");
        for (; i < appointments.size(); i++) {
            System.out.printf("%d. %s\n", i, appointments.get(i).getSlot().getDate());
        }

        // TODO: change
        AppointmentSlot newSlot = new AppointmentSlot(LocalDateTime.now());

        this.patient.rescheduleAppointment(i, newSlot);
    }

    private void cancelAppointment() {

    }

    private void viewScheduledAppointments() {

    }

    private void viewPastAppointments() {

    }
}

