package data.user;

import java.util.ArrayList;

import user.DoctorApiPatient;
import user.UserId;

/**
 * Interface representing an API for managing and retrieving doctor information in the user database,
 * specifically for patients. It provides methods for retrieving doctors, finding a specific doctor by ID,
 * and updating patient data.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public interface UserDatabaseApiPatient {
    /**
     * Retrieves a list of all doctors in the system.
     *
     * @return an ArrayList of DoctorApiPatient objects, representing all doctors available to the patient
     */
    public abstract ArrayList<DoctorApiPatient> getDoctors();

    /**
     * Retrieves a specific doctor from the system based on their unique doctor ID.
     *
     * @param doctorId the unique ID of the doctor to find
     * @return a DoctorApiPatient object corresponding to the provided doctor ID, or null if no doctor is found
     */
    public abstract DoctorApiPatient findDoctorId(UserId doctorId);

    /**
     * Updates the patient data in the system. This method is used to ensure the patient's information 
     * is up-to-date in the user database.
     */
    public abstract void updatePatient();
}
