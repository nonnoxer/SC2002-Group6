package data.appointment;

import java.util.ArrayList;

import appointment.Appointment;
import record.AppointmentOutcomeRecord;
import user.UserId;

public interface AppointmentDatabaseApiDoctor {
    public abstract ArrayList<Appointment> getDoctorAppointments(UserId doctorId);
    public abstract ArrayList<Appointment> getDoctorAppointmentsUpcoming(UserId doctorId);
    public abstract Appointment acceptAppointment(UserId doctorId, int id, boolean accepted);
    public abstract Appointment setOutcome(UserId doctorId, int id, AppointmentOutcomeRecord record);
}
