package data.appointment;

import java.util.ArrayList;

import record.AppointmentOutcomeRecord;

/**
 * This interface defines the methods for a pharmacist to interact with the appointment database.
 * It provides functionality to retrieve appointment records and dispensing prescriptions.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public interface AppointmentDatabaseApiPharmacist {
    /**
     * Retrieves all appointment outcome records from the appointment database.
     *
     * @return a list of AppointmentOutcomeRecord objects, each representing the outcome of an appointment
     */
    public abstract ArrayList<AppointmentOutcomeRecord> getRecords();
    
    /**
     * Dispenses a prescription based on the outcome record of an appointment.
     *
     * @param id the ID of the appointment whose prescription is to be dispensed
     * @return the updated AppointmentOutcomeRecord with the prescription dispensed, or null if no such record exists
     */
    public abstract AppointmentOutcomeRecord dispensePrescription(int id);
}
