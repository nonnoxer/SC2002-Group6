package record;

import java.util.stream.Collectors;
import java.util.Arrays;
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

    public void printAppointmentOutcomeRecord() {
        System.out.printf("Slot: %s\n", this.slot);
        System.out.printf("Type of service: %s\n", this.serviceType);
        System.out.printf("Prescription Status: %s\n", this.prescriptionStatus);
        System.out.printf("Prescription: %s\n", Arrays.stream(this.prescription).map(Medicine::getName).collect(Collectors.joining(", ")));
        System.out.printf("Consultation Notes: %s\n", this.consultationNotes);
    }
}