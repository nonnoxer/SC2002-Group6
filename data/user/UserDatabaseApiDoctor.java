package data.user;

import java.util.ArrayList;

import appointment.Appointment;
import user.Patient;
import user.UserId;

public interface UserDatabaseApiDoctor {
    public abstract Patient findPatientId(UserId id);
    public abstract ArrayList<Patient> getPatients(ArrayList<Appointment> appointments);

}
