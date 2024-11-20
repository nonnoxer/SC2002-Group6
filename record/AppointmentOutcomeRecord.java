package record;

import java.util.stream.Collectors;
import java.io.IOException;
import java.util.ArrayList;
import appointment.AppointmentSlot;
import medicine.Prescription;
import medicine.PrescriptionStatus;

public class AppointmentOutcomeRecord {
    private String serviceType, consultationNotes;
    private PrescriptionStatus prescriptionStatus;
    private AppointmentSlot slot;
    private ArrayList<Prescription> prescription;

    public AppointmentOutcomeRecord(AppointmentSlot slot, String serviceType, String consultationNotes, ArrayList<Prescription> prescription) {
        this.slot = slot;
        this.serviceType = serviceType;
        this.prescriptionStatus = PrescriptionStatus.Pending;
        this.prescription = prescription;
        this.consultationNotes = consultationNotes;
    }

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
        String[] medicineNames = medicines.split("::");
        String[] quantityValues = quantities.split("::");
        if (medicineNames.length != quantityValues.length) {
            throw new IOException("Medicines and quantities do not match");
        }
        this.prescription = new ArrayList<>();
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

    public AppointmentSlot getSlot() {
        return this.slot;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public String getConsultationNotes() {
        return this.consultationNotes;
    }

    public PrescriptionStatus getPrescriptionStatus() {
        return this.prescriptionStatus;
    }
    
    public ArrayList<Prescription> getPrescription() {
        return this.prescription;
    }

    public void updatePrescriptionStatus(PrescriptionStatus status) {
    this.prescriptionStatus = status;
}


    public void printAppointmentOutcomeRecord() {
        System.out.printf("Slot: %s\n", this.slot);
        System.out.printf("Type of service: %s\n", this.serviceType);
        System.out.printf("Prescription Status: %s\n", this.prescriptionStatus);
        System.out.printf("Prescription: %s\n", this.prescription.stream().map(Prescription::getName).collect(Collectors.joining(", ")));
        System.out.printf("Consultation Notes: %s\n", this.consultationNotes);
    }
}
