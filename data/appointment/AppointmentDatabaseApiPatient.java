package data.appointment;

import java.util.ArrayList;

import appointment.Appointment;
import appointment.AppointmentSlot;

public interface AppointmentDatabaseApiPatient {
    public abstract ArrayList<Appointment> getPatientAppointments(String patientId);

    public abstract Appointment newAppointment(String patientId, String doctorId, AppointmentSlot slot);

    public abstract Appointment rescheduleAppointment(String patiendId, int id, AppointmentSlot slot);

    public abstract Appointment cancelAppointment(String patientId, int id);
}
