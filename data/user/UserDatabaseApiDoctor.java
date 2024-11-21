package data.user;

import java.util.ArrayList;

import appointment.Appointment;
import user.Patient;
import user.UserId;

/**
 * Interface representing an API for managing and retrieving patient information in the user database,
 * specifically for doctors. It provides methods for finding patients by ID and retrieving a list of 
 * patients based on their appointments.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public interface UserDatabaseApiDoctor {
    /**
     * Retrieves a patient from the database based on their unique ID.
     *
     * @param id the unique ID of the patient to find
     * @return the Patient object corresponding to the provided ID, or null if no patient with that ID exists
     */
    public abstract Patient findPatientId(UserId id);
    
    /**
     * Retrieves a list of patients associated with the provided appointments.
     *
     * @param appointments a list of appointments from which to derive the patients
     * @return an ArrayList of Patient objects corresponding to the patients in the appointments list
     */
    public abstract ArrayList<Patient> getPatients(ArrayList<Appointment> appointments);

}
