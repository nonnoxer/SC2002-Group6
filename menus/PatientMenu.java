//auth: amu
package menus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.time.LocalDate;

import appointment.Appointment;
import appointment.AppointmentSlot;
import record.AppointmentOutcomeRecord;
import record.MedicalRecord;
import appointment.Schedule;
import medicine.Prescription;
import user.DoctorApiPatient;
import user.Patient;


public class PatientMenu extends Menu{
    private SafeScanner sc;
    private Patient patient;

    public PatientMenu(SafeScanner sc, Patient patient) {
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

            choice = sc.promptInt("Enter your choice: ", 1, 9);
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
                    try {
                        cancelAppointment();
                    } catch (IOException e) {
                        System.out.println("An error occurred: " + e.getMessage());
                    }
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
    }

    private void updatePersonalInfo() {
        MedicalRecord record = this.patient.getMedicalRecord();
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n===== Update Personal Information =====");
            System.out.println("1. Update email address");
            System.out.println("0. Return to main menu");
            System.out.print("Enter your choice: ");

            choice = sc.promptInt("Enter your choice: ", 0, 1);

            switch (choice) {
                case 1:
                    System.out.print("Enter your new email address: ");
                    String newEmail = sc.nextLine();

                    if (newEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) { //gpt
                        record.setContactInfo(newEmail);
                        //WriteFile.writeFile(patient.toCsv());
                        //to save the record after setting?

                        System.out.println("Email address updated successfully.");

                    } else {
                        System.out.println("Invalid email format. Please try again.");
                    }
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private DoctorApiPatient getDoctor() {
        ArrayList<DoctorApiPatient> doctorApis = this.patient.getDoctors();
        System.out.println("Doctors:");
        for (int i = 0; i < doctorApis.size(); i++) {
            System.out.printf("%d. %s\n", i, doctorApis.get(i).getName());
        }
        if (doctorApis.size() == 0) {
            System.out.println("No available doctors.");
            return null;
        }

        int choice = sc.promptInt("Choose a doctor: ", 0, doctorApis.size()-1);
        if (choice < 0 || choice >= doctorApis.size()) {
            System.out.println("Invalid choice.");
            return null;
        }

        return doctorApis.get(choice);
    }

    private ArrayList<AppointmentSlot> getSlots(Schedule schedule) {
        int year = sc.promptInt("Enter year: ", 1900, 2037);
        int month = sc.promptInt("Enter month (1-12): ", 1, 12);

        schedule.printMonth(LocalDate.of(year, month, 1), true);

        int day = sc.promptInt("Enter day: ", 1, 31);

        ArrayList<AppointmentSlot> slots = schedule.getAvailableSlots(LocalDate.of(year, month, day));
        return slots;
    }
    
    private void printSlots(ArrayList<AppointmentSlot> slots) {
        if (slots == null || slots.size() == 0) {
            System.out.println("No available slots.");
            return;
        }

        System.out.println("Available Slots: ");
        for (int i = 0; i < slots.size(); i++) {
            AppointmentSlot slot = slots.get(i);
            if (slot.getAvailability()) {
                System.out.printf("%d - %s\n", i, slot.getDate().toLocalTime().toString());
            }
        }
    }

    private void viewAvailableSlots() {
        DoctorApiPatient doctor = getDoctor();
        Schedule schedule = doctor.getPersonalSchedule();

        ArrayList<AppointmentSlot> slots = getSlots(schedule);
        printSlots(slots);
    }

    private void scheduleAppointment() {
        DoctorApiPatient doctor = getDoctor();
        Schedule schedule = doctor.getPersonalSchedule();

        ArrayList<AppointmentSlot> slots = getSlots(schedule);
        printSlots(slots);
        if (slots.size() == 0) return;

        int index = sc.promptInt("Enter slot number: ", 0, slots.size()-1);
        this.patient.scheduleAppointment(doctor.getId(), slots.get(index));
    }

    private void rescheduleAppointment() {
        ArrayList<Appointment> appointments = this.patient.getScheduledAppointments();

        if (appointments.isEmpty()) {
            System.out.println("You have no scheduled appointments to reschedule.");
            return;
        }

        System.out.println("Choose an appointment to reschedule:");
        for (int i = 0; i < appointments.size(); i++) {
            System.out.printf("%d. %s\n", i, appointments.get(i).getSlot().getDate());
        }
        int appointmentIndex = sc.promptInt("Enter appointment number: ", 0, appointments.size()-1);

        Appointment appointment = appointments.get(appointmentIndex);

        DoctorApiPatient doctor = this.patient.getDoctorById(appointment.getDoctorId());
        Schedule schedule = doctor.getPersonalSchedule();

        ArrayList<AppointmentSlot> slots = getSlots(schedule);
        printSlots(slots);
        if (slots.size() == 0) return;

        int slotIndex = sc.promptInt("Enter slot number: ", 0, slots.size()-1);
        this.patient.rescheduleAppointment(appointment.getId(), slots.get(slotIndex));
    }

    private void cancelAppointment() throws IOException {
        ArrayList<Appointment> appointments = this.patient.getScheduledAppointments();

        if (appointments.isEmpty()) {
            System.out.println("You have no scheduled appointments to cancel.");
            return;
        }

        System.out.println("\n===== Cancel Appointment =====");
        for (int i = 0; i < appointments.size(); i++) {
            System.out.printf("%d. %s with Dr. %s\n",
                    i + 1,
                    appointments.get(i).getSlot().getDate(),
                    // findDoctorNameById(appointments.get(i).getDoctorId(),"Staff_List.csv")
                    appointments.get(i).getDoctorId()
            );
        }

        int choice = sc.promptInt("Enter the number of the appointment you want to cancel (or 0 to return to the menu): ", 0, appointments.size());

        // Handle user input
        if (choice == 0) {
            System.out.println("Returning to main menu...");
            return;
        } else if (choice < 1 || choice > appointments.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        patient.cancelAppointment(appointments.get(choice-1).getId());
        System.out.println("Appointment canceled successfully.");
    }

    private void viewScheduledAppointments() {
        ArrayList<Appointment> appointments = this.patient.getScheduledAppointments();

        if (appointments.isEmpty()) {
            System.out.println("You have no scheduled appointments.");
            return;
        }

        for (Appointment appointment : appointments) {
            System.out.printf("%s - %s\n", 
            appointment.getSlot().getDate(),
            appointment.getAppointmentStatus().toString());
        }
    }

    private void viewPastAppointments() {
        ArrayList<Appointment> appointments = this.patient.getCompletedAppointments();

        if (appointments.isEmpty()) {
            System.out.println("You have no past appointments.");
            return;
        }

        for (Appointment appointment : appointments) {
            System.out.println(appointment.getSlot().getDate());
            AppointmentOutcomeRecord record = appointment.getRecord();
            System.out.println("Service type: " + record.getServiceType());
            System.out.println("Consultation notes: " + record.getConsultationNotes());
            System.out.println("Prescription: " + record.getPrescription().stream().map(Prescription::toString).collect(Collectors.joining(", ")));
            System.out.println("Prescription status: " + record.getPrescriptionStatus());
            System.out.println();
        }
    }
}

