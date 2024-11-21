package user;

import appointment.Schedule;

/**
 * Interface representing the API for operations related to a Doctor's interaction with patients.
 * Provides methods for retrieving a doctor's personal details and schedule.
 * 
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public interface DoctorApiPatient {
    /**
     * Retrieves the unique identifier for the doctor.
     *
     * @return The unique UserId of the doctor.
     */
    public abstract UserId getId();

    /**
     * Retrieves the name of the doctor.
     *
     * @return The name of the doctor.
     */
    public abstract String getName();

    /**
     * Retrieves the personal schedule of the doctor.
     * The schedule includes available slots for appointments and other relevant time periods.
     *
     * @return The doctor's personal schedule.
     */
    public abstract Schedule getPersonalSchedule();
}
