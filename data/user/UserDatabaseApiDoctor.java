package data.user;

import java.util.ArrayList;

import appointment.Appointment;
import user.Patient;

public interface UserDatabaseApiDoctor {
    public abstract Patient findPatientId(String id);
    public abstract ArrayList<Patient> getPatients(ArrayList<Appointment> appointments);

}
