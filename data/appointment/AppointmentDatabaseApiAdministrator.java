package data.appointment;
import java.util.HashMap;
import appointment.Appointment;

public interface AppointmentDatabaseApiAdministrator {
    public abstract HashMap<Integer, Appointment> getAppointments();
}
