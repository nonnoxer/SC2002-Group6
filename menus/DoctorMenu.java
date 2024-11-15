package menus;

import user.Doctor;
import java.util.Scanner;

import appointment.Appointment;
import appointment.AppointmentSlot;
import appointment.Schedule;
import record.MedicalRecord;

import java.time.LocalDate;
import java.util.ArrayList;

import user.Patient;

public class DoctorMenu extends Menu {
    private Scanner sc;
    private Doctor doctor;

    public DoctorMenu(Scanner sc, Doctor doctor) {
        this.sc = sc;
        this.doctor = doctor;
    }

    public void showMenu(){
        int choice = -1;
        
        while (choice != 8) {
            System.out.println("===== Doctor Menu =====");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            handleSelection(choice);
        }
    }

    private void handleSelection(int choice){
        switch (choice) {
            case 1:
                viewPatientRecords();
                break;
            case 2:
                updatePatientRecords();
                break;
            case 3:
                viewSchedule();
                break;
            case 4:
                setAvailability();
                break;
            case 5:
                acceptAppointmentRequest();
                break;
            case 6:
                viewUpcomingAppointments();
                break;
            case 7:
                recordAppointmentOutcome();
                break;
            case 8:
                break;
            default:
                System.out.println("The option is chosen incorrectly, please try again!");
        }
    }

    private Patient selectPatient() {
        System.out.println("Select a patient:");
        ArrayList<String> patientNames = doctor.getPatientNames();
        for (int i = 0; i < patientNames.size(); i++) {
            System.out.printf("%d. %s\n", i, patientNames.get(i));
        }
        int choice = sc.nextInt();
        if (choice < 0 || choice >= patientNames.size()) {
            System.out.println("Invalid choice.");
            return null;
        }

        Patient selectedPatient = doctor.getPatientIndex(choice);
        return selectedPatient;
    }

    private void viewPatientRecords() {
        Patient selectedPatient = selectPatient();
        if (selectedPatient == null) return;
    
        System.out.printf("Viewing records of %s:\n", selectedPatient.getName());
        // Get records
        MedicalRecord record = selectedPatient.getMedicalRecord();
        System.out.printf("ID: %s\n", record.getId());
        System.out.printf("Name: %s\n", record.getName());
        System.out.printf("Birth Date: %s\n", record.getBirthDate());
        System.out.printf("Gender: %s\n", record.getGender());
        System.out.printf("Contact Information: %s\n", record.getContactInfo());
        System.out.printf("Blood Type: %s\n", record.getBloodType());
        System.out.printf("Appointment Record: ");
        record.getPastAppointments().forEach(appointment -> {
            appointment.printAppointmentOutcomeRecord(); 
            System.out.println();
        });
    }

    private void updatePatientRecords() {
        Patient selectedPatient = selectPatient();
        if (selectedPatient == null) return;

        System.out.printf("Updating records of %s:\n", selectedPatient.getName());
        // Update records
    }

    private void viewSchedule() {
        Schedule schedule = this.doctor.getPersonalSchedule();

        System.out.print("Enter year: ");
        int year = sc.nextInt();
        System.out.print("Enter month (1-12): ");
        int month = sc.nextInt();

        schedule.printMonth(LocalDate.of(year, month, 1), false);

        System.out.print("Enter day: ");
        int day = sc.nextInt();

        ArrayList<AppointmentSlot> slots = schedule.getSlots(LocalDate.of(year, month, day));
        if (slots == null || slots.size() == 0) {
            System.out.println("No slots found.");
            return;
        }

        for (int i = 0; i < slots.size(); i++) {
            AppointmentSlot slot = slots.get(i);
            String available = slot.getAvailability() ? "Available" : "Not Available";
            System.out.println(slot.getDate() + ": " + available);
        }
    }

    private void setAvailability() {
        this.doctor.setAvailability();
    }

    private void acceptAppointmentRequest() {

    }

    private void viewUpcomingAppointments() {
        ArrayList<Appointment> appointments = this.doctor.getUpcomingAppointments();
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            System.out.println(appointment.getSlot().getDate());
        }
    }

    private void recordAppointmentOutcome() {
        ArrayList<Appointment> appointments = this.doctor.getUpcomingAppointments();
        System.out.println("Select an appointment:");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            System.out.printf("%d. %s", i, appointment.getSlot().getDate());
        }
        int choice = sc.nextInt();

    }
}