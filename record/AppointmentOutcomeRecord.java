package record;

import appointment.AppointmentSlot;
import medicine.Medicine;

public class AppointmentOutcomeRecord {
    private String serviceType, consultationNotes, prescriptionStatus;
    private AppointmentSlot slot;
    private Medicine[] prescription;

    public AppointmentOutcomeRecord(AppointmentSlot slot, String serviceType, String consultationNotes) {
        this.slot = slot;
        this.serviceType = serviceType;
        this.prescriptionStatus = "Pending";
        this.prescription = null;
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
    
    public Medicine[] getPrescription() {
        return this.prescription;
    }
}