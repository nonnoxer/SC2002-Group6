package record;

import java.util.stream.Collectors;
import java.io.IOException;
import java.util.ArrayList;

import appointment.AppointmentSlot;
import medicine.Prescription;
import medicine.PrescriptionStatus;

/**
 * The AppointmentOutcomeRecord class represents the outcome of a medical appointment.
 * It includes details about the appointment slot, service type, prescription status,
 * consultation notes, diagnoses, treatment plan, and prescription. It provides methods 
 * to manage and print appointment outcome information.
 * 
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class AppointmentOutcomeRecord {
    private int appointmentId;
    private String serviceType, consultationNotes;
    private PrescriptionStatus prescriptionStatus;
    private ArrayList<String> diagnoses = new  ArrayList<>();
    private ArrayList<String> treatmentPlan = new ArrayList<>();
    private AppointmentSlot slot;
    private ArrayList<Prescription> prescription;

    /**
     * Constructs a new AppointmentOutcomeRecord with the given appointment slot,
     * service type, consultation notes, prescription, diagnosis, and treatment plan.
     * The prescription status is set to "Pending" by default.
     *
     * @param slot the AppointmentSlot representing the appointment time and details
     * @param serviceType the type of service provided during the appointment
     * @param consultationNotes the notes from the consultation
     * @param prescription an ArrayList of Prescription objects prescribed during the appointment
     * @param diagnosis the diagnosis made during the appointment
     * @param treatmentPlan the treatment plan recommended during the appointment
     */
    public AppointmentOutcomeRecord(AppointmentSlot slot, String serviceType, String consultationNotes, ArrayList<Prescription> prescription, String diagnosis, String treatmentPlan) {
        this.slot = slot;
        this.serviceType = serviceType;
        this.prescriptionStatus = PrescriptionStatus.Pending;
        this.prescription = prescription;
        this.consultationNotes = consultationNotes;
        this.diagnoses.add(diagnosis);
        this.treatmentPlan.add(treatmentPlan);
    }

    /**
     * Constructs an AppointmentOutcomeRecord from a CSV line.
     * 
     * @param line an array of strings representing a line of appointment data
     * @throws IOException if the data is malformed or invalid
     */
    public AppointmentOutcomeRecord(String[] line) throws IOException {
        this.serviceType = line[0];
        this.consultationNotes = line[1];
        try {
            this.prescriptionStatus = PrescriptionStatus.valueOf(line[2]);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid line: expected " + line[2] + " to be one of 'Pending', 'Dispensed'.");
        }

        String medicines = line[3];
        String quantities = line[4];
        String diagnoses = line[5];
        String treatmentPlan = line[6];
        String[] medicineNames = medicines.isEmpty() ? new String[]{} : medicines.split("::");
        String[] quantityValues = quantities.isEmpty() ? new String[]{} : quantities.split("::");
        String[] diagnosesList = diagnoses.isEmpty() ? new String[]{} : diagnoses.split("::");
        String[] treatmentPlanList = treatmentPlan.isEmpty() ? new String[]{} : treatmentPlan.split("::");
        if (medicineNames.length != quantityValues.length) {
            throw new IOException("Medicines and quantities do not match");
        }
        this.prescription = new ArrayList<>();
        if (medicineNames.length>0){
            for (int i = 0; i < medicineNames.length; i++) {
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityValues[i]);
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid line: expected " + quantityValues[i] + " to be an integer.");
                }
                prescription.add(new Prescription(medicineNames[i], quantity));
            }
        }
        for (int i = 0; i < diagnosesList.length; i++) {
            this.diagnoses.add(diagnosesList[i]);
        }
        for (int i = 0; i < treatmentPlanList.length; i++) {
            this.treatmentPlan.add(treatmentPlanList[i]);
        }
    }

    /**
     * Gets the appointment ID.
     *
     * @return the appointment ID
     */
    public int getAppointmentId() {
        return this.appointmentId;
    }

    /**
     * Gets the appointment slot.
     *
     * @return the AppointmentSlot for the appointment
     */
    public AppointmentSlot getSlot() {
        return this.slot;
    }

    /**
     * Gets the service type provided during the appointment.
     *
     * @return the service type
     */
    public String getServiceType() {
        return this.serviceType;
    }

    /**
     * Gets the consultation notes from the appointment.
     *
     * @return the consultation notes
     */
    public String getConsultationNotes() {
        return this.consultationNotes;
    }

    /**
     * Gets the prescription status.
     *
     * @return the PrescriptionStatus of the appointment
     */
    public PrescriptionStatus getPrescriptionStatus() {
        return this.prescriptionStatus;
    }
    
    /**
     * Gets the list of prescriptions prescribed during the appointment.
     *
     * @return an ArrayList of Prescription objects
     */
    public ArrayList<Prescription> getPrescription() {
        return this.prescription;
    }

    /**
     * Gets the list of diagnoses made during the appointment.
     *
     * @return an ArrayList of diagnosis strings
     */
    public ArrayList<String> getDiagnoses(){
        return this.diagnoses;
    }


    /**
     * Gets the treatment plan recommended during the appointment.
     *
     * @return an ArrayList of treatment plan strings
     */
    public ArrayList<String> getTreatmentPlan(){
        return this.treatmentPlan;
    }
    
    /**
     * Adds a new diagnosis to the list of diagnoses.
     *
     * @param diagnosis the diagnosis to add
     */
    public void addDiagnoses(String diagnoses){
        this.diagnoses.add(diagnoses);
    }
    
    /**
     * Adds a new treatment plan to the list of treatment plans.
     *
     * @param treatmentPlan the treatment plan to add
     */
    public void addTreatmentPlan(String treatmentPlan){
        this.treatmentPlan.add(treatmentPlan);
    }


    /**
     * Adds new prescriptions to the list of prescriptions. If the prescription already
     * exists, the quantity is updated.
     * 
     * @param prescriptions a list of prescriptions to add
     * @return 1 if the prescription was successfully added, 0 if the prescription status is "Dispensed"
     */
    public int addPrescription(ArrayList<Prescription> prescriptions) {
        if (this.prescriptionStatus == PrescriptionStatus.Dispensed) {
            return 0;
        } else {
            for (Prescription newPrescription : prescriptions) {
                boolean found = false;
                for (Prescription existingPrescription : this.prescription) {
                    if (existingPrescription.getName().equals(newPrescription.getName())) {
                        existingPrescription.setQuantity(existingPrescription.getQuantity() + newPrescription.getQuantity());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    this.prescription.add(newPrescription);
                }
            }
            return 1;
        }
    }

    /**
     * Sets the appointment ID.
     *
     * @param appointmentId the ID to set for the appointment
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Sets the appointment slot.
     *
     * @param slot the AppointmentSlot to set
     */
    public void setSlot(AppointmentSlot slot) {
        this.slot = slot;
    }

    /**
     * Marks the prescription as dispensed, changing the prescription status to "Dispensed".
     */
    public void dispensePrescription() {
        this.prescriptionStatus = PrescriptionStatus.Dispensed;
    }

    /**
     * Prints the appointment outcome record to the console, including the slot details,
     * service type, prescription status, prescriptions, consultation notes, diagnoses, 
     * and treatment plan.
     */
    public void printAppointmentOutcomeRecord() {
        System.out.printf("Slot: %s\n", this.slot.getDate().toString());
        if (this.serviceType.isEmpty()){
            System.out.printf("Service Type: None\n");
        } else{
            System.out.printf("Type of service: %s\n", this.serviceType);
        }
        System.out.printf("Prescription Status: %s\n", this.prescriptionStatus);
        System.out.printf("Prescription: %s\n", this.prescription.stream().map(Prescription::getName).collect(Collectors.joining(", ")));
        if (this.consultationNotes.isEmpty()){
            System.out.printf("Consultation Note: None\n");
        }else{
            System.out.printf("Consultation Notes: %s\n", this.consultationNotes);
        }
        if (this.diagnoses.isEmpty()) {
            System.out.printf("Diagnosis: No diagnosis thus far\n");
        } else {
            System.out.printf("Diagnosis: %s\n", this.diagnoses.stream().collect(Collectors.joining(", ")));
        }
        
        if (this.treatmentPlan.isEmpty()) {
            System.out.printf("Treatment Plan: No treatment plan thus far\n");
        } else {
            System.out.printf("Treatment Plan: %s\n", this.treatmentPlan.stream().collect(Collectors.joining(", ")));
        }
    }
}
