package data.appointment;

import java.util.ArrayList;

import appointment.Appointment;
import record.AppointmentOutcomeRecord;

public interface AppointmentDatabaseApiDoctor {
    public abstract ArrayList<Appointment> getDoctorAppointments(String doctorId);
    public abstract ArrayList<Appointment> getDoctorAppointmentsUpcoming(String doctorId);
    public abstract Appointment acceptAppointment(String doctorId, int id, boolean accepted);
    public abstract Appointment setOutcome(String doctorId, int id, AppointmentOutcomeRecord record);
}
