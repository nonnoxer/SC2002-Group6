package record;

import java.util.stream.Collectors;
import java.io.IOException;
import java.util.ArrayList;

import appointment.AppointmentSlot;
import medicine.Prescription;
import medicine.PrescriptionStatus;

public class AppointmentOutcomeRecord {
    private int appointmentId;
    private String serviceType, consultationNotes;
    private PrescriptionStatus prescriptionStatus;
    private ArrayList<String> diagnoses = new  ArrayList<>();
    private ArrayList<String> treatmentPlan = new ArrayList<>();
    private AppointmentSlot slot;
    private ArrayList<Prescription> prescription;

    public AppointmentOutcomeRecord(AppointmentSlot slot, String serviceType, String consultationNotes, ArrayList<Prescription> prescription, String diagnosis, String treatmentPlan) {
        this.slot = slot;
        this.serviceType = serviceType;
        this.prescriptionStatus = PrescriptionStatus.Pending;
        this.prescription = prescription;
        this.consultationNotes = consultationNotes;
        this.diagnoses.add(diagnosis);
        this.treatmentPlan.add(treatmentPlan);
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

    public int getAppointmentId() {
        return this.appointmentId;
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

    public ArrayList<String> getDiagnoses(){
        return this.diagnoses;
    }

    public ArrayList<String> getTreatmentPlan(){
        return this.treatmentPlan;
    }
    
    public void addDiagnoses(String diagnoses){
        this.diagnoses.add(diagnoses);
    }
    
    public void addTreatmentPlan(String treatmentPlan){
        this.treatmentPlan.add(treatmentPlan);
    }

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

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setSlot(AppointmentSlot slot) {
        this.slot = slot;
    }

    public void dispensePrescription() {
        this.prescriptionStatus = PrescriptionStatus.Dispensed;
    }


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
