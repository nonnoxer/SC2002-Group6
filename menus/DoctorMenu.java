package menus;

import user.Doctor;

import appointment.Appointment;
import appointment.AppointmentSlot;
import appointment.Schedule;
import record.AppointmentOutcomeRecord;
import record.MedicalRecord;
import medicine.Inventory;
import medicine.Medicine;
import medicine.Prescription;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import user.Patient;

public class DoctorMenu extends Menu {
    private SafeScanner sc;
    private Doctor doctor;

    public DoctorMenu(SafeScanner sc, Doctor doctor) {
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

            choice = sc.promptInt("Enter your choice: ", 1, 8);
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
        ArrayList<Patient> patients = doctor.getPatients();
        if (patients.size() == 0){
            System.out.println("No patients!");
            return null;
        }
        else{
            System.out.println("Select a patient:");
            for (int i = 0; i < patients.size(); i++) {
                System.out.printf("%d. %s\n", i, patients.get(i).getName());
            }
            int choice = sc.promptInt("", 0, patients.size()-1);

            Patient selectedPatient = doctor.getPatientIndex(patients.get(choice).getId());
            return selectedPatient;
        }
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
        if (!availibility){
            // if its true its easy, dont need to check appointment 
            for (int i = 0; i < slotToChange.size(); i++) {
                tmp = slots.get(slotToChange.get(i));
                tmp.setAvailability(availibility);
            }
        } else{
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
            }            
        }
    }

    private void acceptAppointmentRequest() {
        ArrayList<Appointment> appointments = this.doctor.getPendingAppointments();
        if (appointments.size() == 0) {
            System.out.println("No pending appointments.");
            return;
        }

        System.out.println("Select an appointment:");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            System.out.printf("%d. %s\n", i, appointment.getSlot().getDate().toString());
        }
        int choice = sc.promptInt("", 0, appointments.size()-1);
        System.out.println("0. Accept");
        System.out.println("1. Decline");
        int accept = sc.promptInt("Enter your choice: ", 0, 1);
        boolean accepted = accept == 0;
        this.doctor.acceptRequest(appointments.get(choice).getId(), accepted);
    }

    private void viewUpcomingAppointments() {
        ArrayList<Appointment> appointments = this.doctor.getUpcomingAppointments();
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            System.out.println(appointment.getSlot().getDate());
        }
    }

    private void recordAppointmentOutcome() {
        ArrayList<Prescription> selectedMedicines = new ArrayList<>();
        ArrayList<Appointment> appointments = this.doctor.getUpcomingAppointments();
        System.out.println("Select an appointment:");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            System.out.printf("%d. %s", i, appointment.getSlot().getDate());
        }
        int appointmentChoice = sc.promptInt("", 0, appointments.size()-1);

        // Finding patient appointment
        AppointmentSlot slot = appointments.get(appointmentChoice).getSlot();
        // filling up the form
        String serviceType = sc.promptLine("Enter service type: ");
        String consultationNotes = sc.promptLine("Enter consultation notes: ");
        // medicine for patient
        Inventory inventory = this.doctor.getInventory();
        ArrayList<Medicine> medicines = inventory.getInventory();
        int choice = -1;
        while(choice != medicines.size()+1){
            System.out.println("Medicine List:");
            for(int i=0; i<medicines.size(); i++){
                System.out.printf("%d: %s (stock: %d)\n", i+1, medicines.get(i).getName(), medicines.get(i).getStock());
            }
            // if press size+1 for first time means no medicine neeeded
            System.out.printf("%d: Done with selection!\n", medicines.size()+1);
            choice = sc.promptInt("", 1, medicines.size()+1);
            if (choice != medicines.size()+1){
                String name = medicines.get(choice-1).getName();
                int quantity = sc.promptInt("Enter medicine quantity: ", 1, medicines.get(choice-1).getStock());
                selectedMedicines.add(new Prescription(name, quantity));
            }
            else{
                break;
            }
        }
        AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(slot, serviceType, consultationNotes, selectedMedicines);
        this.doctor.recordOutcome(appointments.get(appointmentChoice).getId(), outcomeRecord);
    }
}