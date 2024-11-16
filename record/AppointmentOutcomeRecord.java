package record;

import java.util.stream.Collectors;
import java.util.ArrayList;
import appointment.AppointmentSlot;
import medicine.Medicine;

public class AppointmentOutcomeRecord {
    private String serviceType, consultationNotes, prescriptionStatus;
    private AppointmentSlot slot;
    private ArrayList<Medicine> prescription;

    public AppointmentOutcomeRecord(AppointmentSlot slot, String serviceType, String consultationNotes, ArrayList<Medicine> prescription) {
        this.slot = slot;
        this.serviceType = serviceType;
        this.prescriptionStatus = "Pending";
        this.prescription = prescription;
        this.consultationNotes = consultationNotes;
    }

    public AppointmentOutcomeRecord(AppointmentSlot slot, String serviceType, String consultationNotes, String prescriptionStatus, ArrayList<Medicine> prescription) {
        this.slot = slot;
        this.serviceType = serviceType;
        this.prescriptionStatus = prescriptionStatus;
        this.prescription = prescription;
        this.consultationNotes = consultationNotes;
    }

    public AppointmentSlot getSlot() {
        return this.slot;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public String getConsultationNotes() {
        return this.consultationNotes;
    }

    public String getPrescriptionStatus() {
        return this.prescriptionStatus;
    }
    
    public ArrayList<Medicine> getPrescription() {
        return this.prescription;
    }

    public void updatePrescriptionStatus(String status) {
    this.prescriptionStatus = status;
}


    public void printAppointmentOutcomeRecord() {
        System.out.printf("Slot: %s\n", this.slot);
        System.out.printf("Type of service: %s\n", this.serviceType);
        System.out.printf("Prescription Status: %s\n", this.prescriptionStatus);
        System.out.printf("Prescription: %s\n", this.prescription.stream().map(Medicine::getName).collect(Collectors.joining(", ")));
        System.out.printf("Consultation Notes: %s\n", this.consultationNotes);
    }
}
