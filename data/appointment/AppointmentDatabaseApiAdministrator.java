package data.appointment;
import java.util.HashMap;
import appointment.Appointment;

/**
 * This interface defines the methods for an administrator to interact with the appointment database.
 * It provides functionality to retrieve the list of all appointments in the system.
 * @author LOW KAN YUI (LIU GENGRUI)
 * @version 1.0
 * @since 2024-11-21
 */
public interface AppointmentDatabaseApiAdministrator {
    /**
     * Retrieves all appointments from the appointment database.
     *
     * @return a HashMap containing all appointments, where the key is the appointment ID 
     *         and the value is the corresponding Appointment object
     */
    public abstract HashMap<Integer, Appointment> getAppointments();
}
