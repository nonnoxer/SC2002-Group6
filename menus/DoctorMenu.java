package menus;

import user.Doctor;

import appointment.Appointment;
import appointment.AppointmentSlot;
import appointment.Schedule;
import record.AppointmentOutcomeRecord;
import record.MedicalRecord;
import medicine.Medicine;
import medicine.Prescription;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import user.Patient;

/**
 * The DoctorMenu class provides a menu interface for the doctor to interact with the system.
 * It allows the doctor to perform various actions such as viewing and updating patient medical records, 
 * managing appointments, and recording outcomes of consultations.
 * 
 * @author LOW KAN YUI (LIU GENGRUI)
 * @version 1.0
 * @since 2024-11-21
 */
public class DoctorMenu extends Menu {
    private SafeScanner sc;
    private Doctor doctor;

    /**
     * Constructs a DoctorMenu object.
     * 
     * @param sc the SafeScanner object used to capture user input
     * @param doctor the Doctor object associated with the menu
     */
    public DoctorMenu(SafeScanner sc, Doctor doctor) {
        this.sc = sc;
        this.doctor = doctor;
    }

    /**
     * Displays the doctor menu and prompts the doctor for an action.
     * This method keeps looping until the doctor chooses to log out.
     */
    public void showMenu(){
        int choice = -1;
        
        while (choice != 0) {
            System.out.println("\n===== Doctor Menu =====");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("0. Logout");

            choice = sc.promptInt("Enter your choice: ", 0, 7);
            handleSelection(choice);
        }
    }

    /**
     * Handles the user's selection based on the menu choice.
     * 
     * @param choice the user's menu choice
     */
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
            case 0:
                break;
            default:
                System.out.println("The option is chosen incorrectly, please try again!");
        }
    }

    /**
     * Allows the doctor to select a patient from the list of patients they are treating.
     * 
     * @return the selected Patient object, or null if the user cancels or there are no patients
     */
    private Patient selectPatient() {
        ArrayList<Patient> patientsList = doctor.getPatients();
        HashSet<Patient> uniquePatients = new HashSet<>(patientsList);
        ArrayList<Patient> patients = new ArrayList<>(uniquePatients);

        if (uniquePatients.size() == 0){
            System.out.println("No patients!");
            return null;
        }
        else{
            System.out.println("Select a patient:");
            for (int i = 0; i < patients.size(); i++) {
                System.out.printf("%d. %s\n", i+1, patients.get(i).getName());
            }
            System.out.println("0. Cancel");
            int choice = sc.promptInt("", 0, patients.size());
            if (choice == 0) return null;

            Patient selectedPatient = doctor.getPatientIndex(patients.get(choice-1).getId());
            return selectedPatient;
        }
    }

    /**
     * Displays the medical records of a selected patient.
     */
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
        System.out.printf("Appointment Record: \n");

        ArrayList<Appointment> appointments = selectedPatient.getCompletedAppointments();
        for (Appointment appointment: appointments) {
            appointment.getRecord().printAppointmentOutcomeRecord(); 
            System.out.println();
        }
    }

    /**
     * Allows the doctor to update the medical records of a selected patient.
     */
    private void updatePatientRecords() {
        ArrayList<Prescription> selectedMedicines = new ArrayList<>();
        Patient selectedPatient = selectPatient();
        if (selectedPatient == null) return;

        System.out.printf("Updating records of %s:\n", selectedPatient.getName());
        // Update records
        ArrayList<Appointment> appointments = selectedPatient.getCompletedAppointments();
        System.out.println("Select an appointment:");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            System.out.printf("%d. %s\n", i+1, appointment.getSlot().getDate());
        }
        System.out.println("0. Cancel");
        int appointmentChoice = sc.promptInt("Enter your choice: ", 0, appointments.size());
        if (appointmentChoice == 0) return;
        AppointmentOutcomeRecord record = appointments.get(appointmentChoice-1).getRecord();
        if (record == null){
            System.err.println("No records!");
            return;
        }

        String newDiagnosis = sc.promptLine("Enter new diagnosis: ");
        record.addDiagnoses(newDiagnosis);

        String newTreatmentPlan = sc.promptLine("Enter new treatment plan: ");
        record.addTreatmentPlan(newTreatmentPlan);
    
        // Adding a prescription
        ArrayList<Medicine> medicines = this.doctor.getInventory();
        int choice = -1;
        while(choice != medicines.size()+1){
            System.out.println("Medicine List:");
            for(int i=0; i<medicines.size(); i++){
                System.out.printf("%d. %s (stock: %d)\n", i+1, medicines.get(i).getName(), medicines.get(i).getStock());
            }
            System.out.println("0. Done with selection!");
            choice = sc.promptInt("", 0, medicines.size());
            if (choice != 0){
                String name = medicines.get(choice-1).getName();
                int quantity = sc.promptInt("Enter medicine quantity: ", 1, medicines.get(choice-1).getStock());
                selectedMedicines.add(new Prescription(name, quantity));
            }
            else{
                break;
            }
        }
        if (record.addPrescription(selectedMedicines)==0){
            System.out.println("Unable to update record, medicine is already dispensed!");
        }
        this.doctor.recordOutcome(appointments.get(appointmentChoice-1).getId(), record);
    }

    /**
     * Displays the doctor's personal schedule for a given month and year, and allows the doctor to view available slots.
     */
    private void viewSchedule() {
        Schedule schedule = this.doctor.getPersonalSchedule();

        int year = sc.promptInt("Enter year: ", 1900, 2037);
        int month = sc.promptInt("Enter month (1-12): ", 1, 12);

        schedule.printMonth(LocalDate.of(year, month, 1), false);

        int day = sc.promptInt("Enter day: ", 1, 31);

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

    /**
     * Allows the doctor to set availability for appointments on specific days and slots.
     */
    private void setAvailability() {
        AppointmentSlot tmp;
        Schedule schedule = this.doctor.getPersonalSchedule();

        int year = sc.promptInt("Enter year: ", 1900, 2037);
        int month = sc.promptInt("Enter month (1-12): ", 1, 12);

        schedule.printMonth(LocalDate.of(year, month, 1), false);

        int day = sc.promptInt("Enter day: ", 1, 31);

        ArrayList<AppointmentSlot> slots = schedule.getSlots(LocalDate.of(year, month, day));
        if (slots == null || slots.size() == 0) {
            System.out.println("No slots found.");
            return;
        }

        for (int i = 0; i < slots.size(); i++) {
            AppointmentSlot slot = slots.get(i);
            String available = slot.getAvailability() ? "Available" : "Not Available";
            System.out.printf("Slot %-2d: %s: %s\n", i, slot.getDate(), available);
        }
        List<Integer> slotToChange = sc.promptRange("Slot to change: ", 0, slots.size());
        boolean availibility = sc.promptInt("Set availablility (available: 1, unavailable: 0): ", 0, 1) == 1;
        boolean flag = true;
        ArrayList<Appointment> appointments = new ArrayList<>();
        
        if (this.doctor.getUpcomingAppointments() != null) {
            appointments.addAll(this.doctor.getUpcomingAppointments());
        }
        if (this.doctor.getPendingAppointments() != null) {
            appointments.addAll(this.doctor.getPendingAppointments());
        }
        for (int i = 0; i < slotToChange.size(); i++) {
            flag = true;
            tmp = slots.get(slotToChange.get(i));
            for (int j = 0; j < appointments.size(); j++) {
                Appointment appointment = appointments.get(j);
                if (appointment.getSlot().equals(tmp)) {
                    flag = false;
                    break;
                }
            }
            if (flag == false) {
                break;
            }
        }
        if (flag) {
            for (int i = 0; i < slotToChange.size(); i++) {
                tmp = slots.get(slotToChange.get(i)); 
                tmp.setAvailability(availibility); 
            }
        } else {
            System.out.println("One or more slots have an appointment, unable to change availability!");
            return;
        }          
        System.out.println("Changed successfully!");
    }

    /**
     * Allows the doctor to accept or decline pending appointment requests.
     */
    private void acceptAppointmentRequest() {
        ArrayList<Appointment> appointments = this.doctor.getPendingAppointments();
        if (appointments.size() == 0) {
            System.out.println("No pending appointments.");
            return;
        }

        System.out.println("Select an appointment:");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            System.out.printf("%d. %s\n", i+1, appointment.getSlot().getDate().toString());
        }
        System.out.println("0. Cancel");
        int choice = sc.promptInt("Enter your choice: ", 0, appointments.size());
        if (choice == 0) return;
        System.out.println("1. Accept");
        System.out.println("0. Decline");
        int accept = sc.promptInt("Enter your choice: ", 0, 1);
        boolean accepted = accept == 1;
        this.doctor.acceptRequest(appointments.get(choice-1).getId(), accepted);
        if (accepted) System.out.println("Successfully accepted appointment.");
        else System.out.println("Successfully declined appointment.");
    }

    private void viewUpcomingAppointments() {
        ArrayList<Appointment> appointments = this.doctor.getUpcomingAppointments();
        if (appointments.size() == 0){
            System.out.println("No appointments!");
        }
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            System.out.printf(
                "%s - %s\n",
                appointment.getSlot().getDate(),
                this.doctor.getPatientIndex(appointment.getPatientId()).getName());
        }
    }

    private void recordAppointmentOutcome() {
        ArrayList<Prescription> selectedMedicines = new ArrayList<>();
        ArrayList<Appointment> appointments = this.doctor.getUpcomingAppointments();
        if (appointments.size() == 0){
            System.out.println("No appointments");
            return;
        }
        System.out.println("Select an appointment:");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            System.out.printf("%d. %s\n", i+1, appointment.getSlot().getDate());
        }
        System.out.println("0. Cancel");
        int appointmentChoice = sc.promptInt("Enter your choice: ", 0, appointments.size());
        if (appointmentChoice == 0) return;

        // Finding patient appointment
        AppointmentSlot slot = appointments.get(appointmentChoice-1).getSlot();
        // filling up the form
        String serviceType = sc.promptLine("Enter service type: ");
        String consultationNotes = sc.promptLine("Enter consultation notes: ");
        String diagnosis = sc.promptLine("Enter diagnosis: ");
        String treatmentPlan = sc.promptLine("Enter treatment plan, if any: ");
        // medicine for patient
        ArrayList<Medicine> medicines = this.doctor.getInventory();
        int choice = -1;
        while(choice != medicines.size()+1){
            System.out.println("Medicine List:");
            for(int i=0; i<medicines.size(); i++){
                System.out.printf("%d. %s (stock: %d)\n", i+1, medicines.get(i).getName(), medicines.get(i).getStock());
            }
            // if press size+1 for first time means no medicine neeeded
            System.out.println("0. Done with selection!");
            choice = sc.promptInt("Enter your choice: ", 0, medicines.size());
            if (choice != 0){
                String name = medicines.get(choice-1).getName();
                int quantity = sc.promptInt("Enter medicine quantity: ", 1, medicines.get(choice-1).getStock());
                selectedMedicines.add(new Prescription(name, quantity));
            }
            else{
                break;
            }
        }
        AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(slot, serviceType, consultationNotes, selectedMedicines, diagnosis, treatmentPlan);
        this.doctor.recordOutcome(appointments.get(appointmentChoice-1).getId(), outcomeRecord);
        System.out.println("Marked appointment as complete.");
    }
}