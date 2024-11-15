//auth: amu
package menus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;

import appointment.Appointment;
import appointment.AppointmentSlot;
import data.ReadFile;
import data.WriteFile;
import record.AppointmentOutcomeRecord;
import record.MedicalRecord;
import user.*;
import appointment.Appointment;
import appointment.AppointmentSlot;
import appointment.Schedule;
import data.WriteFile;
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

            try {
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter your new email address: ");
                        String newEmail = sc.nextLine();

                            if (newEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) { //gpt
                                record.setContactInfo(newEmail);
                                //to save the record after setting contact

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
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine();
                }
            }
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
        Schedule schedule = doctor.getPersonalSchedule();

        System.out.print("Enter year: ");
        int year = sc.nextInt();
        System.out.print("Enter month (1-12): ");
        int month = sc.nextInt();

        schedule.printMonth(LocalDate.of(year, month, 1), true);

        System.out.print("Enter day: ");
        int day = sc.nextInt();

        ArrayList<AppointmentSlot> slots = schedule.getSlots(LocalDate.of(year, month, day));
        if (slots == null || slots.size() == 0) {
            System.out.println("No available slots.");
            return;
        }

        System.out.println("Available Slots: ");
        for (AppointmentSlot slot : slots) {
            if (slot.getAvailability()) {
                System.out.printf("%s\n", slot.getDate().toString());
            }
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

        private void cancelAppointment() throws IOException {
            ArrayList<Appointment> appointments = this.patient.getAppointments();

            if (appointments.isEmpty()) {
                System.out.println("You have no scheduled appointments to cancel.");
                return;
            }

            System.out.println("\n===== Cancel Appointment =====");
            for (int i = 0; i < appointments.size(); i++) {
                System.out.printf("%d. %s with Dr. %s\n",
                        i + 1,
                        appointments.get(i).getSlot().getDate(),
                        findDoctorNameById(appointments.get(i).getDoctorId(),"Staff_List.csv")
                );
            }

            System.out.print("Enter the number of the appointment you want to cancel (or 0 to return to the menu): ");
            try {
                int choice = sc.nextInt();
                sc.nextLine(); // Consume the newline character

                // Handle user input
                if (choice == 0) {
                    System.out.println("Returning to main menu...");
                    return;
                } else if (choice < 1 || choice > appointments.size()) {
                    System.out.println("Invalid choice. Please try again.");
                    return;
                }

                Appointment selectedAppointment = appointments.get(choice - 1);
                selectedAppointment.patientCancel();
                System.out.println("Appointment canceled successfully.");
            }catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // Clear invalid input
            }
        }


        private void viewScheduledAppointments() {

        }

        private void viewPastAppointments() {

        }

        public static String findDoctorNameById(String doctorId, String staffFilePath) throws IOException { // this is quite a fucked function but im too stupid to figure it out
            ArrayList<Staff> staffList = ReadFile.readStaffListFile(staffFilePath);
            for (User user : staffList) {
                if (user instanceof Doctor && user.getID().equals(doctorId)) {
                    return user.getName();
                }
            }
            return "somethings wrong it should never reach here, the doctor id does not exist in the staff file";
        }
    }

