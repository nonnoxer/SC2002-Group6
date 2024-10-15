package appointment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Appointment {

    private Date dateOfAppointment;  // Date of the appointment
    private String serviceType;      // Type of service provided
    private List<medication> medications;  // List of prescribed medications
    private String consultationNotes;  // Notes from the consultation

    // Constructor to initialize the appointment outcome
    public appointmentOutcome(Date dateOfAppointment, String serviceType, String consultationNotes) {
    	
        this.dateOfAppointment = dateOfAppointment;
        this.serviceType = serviceType;
        this.consultationNotes = consultationNotes;
        this.medications = new ArrayList<>();  // Initialize an empty list of medications
        
    }

    // Method to add a prescribed medication
    public void addMedication(String medicationName) {
    	
        medication medication = new medication(medicationName);
        medications.add(medication);
        System.out.println("Added medication: " + medicationName + " with status 'pending'.");
        
    }

    // Method to display appointment details
    public void displayAppointmentOutcome() {
    	
        System.out.println("===== Appointment Outcome =====");
        System.out.println("Date of Appointment: " + dateOfAppointment);
        System.out.println("Service Provided: " + serviceType);
        System.out.println("Consultation Notes: " + consultationNotes);

        // Display each prescribed medication
        System.out.println("Prescribed Medications:");
        for (medication med : medications) {
        	
            med.displayMedication();  // Display medication name and status
            
        }
        
        System.out.println("================================");
        
    }

    // Method to update medication status
    public void updateMedicationStatus(String medicationName, String newStatus) {
    	
        for (medication med : medications) {
        	
            if (med.getName().equalsIgnoreCase(medicationName)) {
            	
                med.setStatus(newStatus);
                System.out.println("Updated " + medicationName + " status to " + newStatus);
                return;
                
            }
        }
        
        System.out.println("Medication not found: " + medicationName);
        
    }
    
    public Date getDateOfAppointment() {
    	
        return dateOfAppointment;
    }

    // Getters and Setters for other fields if needed (omitted for brevity)
}

