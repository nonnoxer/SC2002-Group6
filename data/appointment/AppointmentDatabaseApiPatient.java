package data.appointment;

import java.util.ArrayList;

import appointment.Appointment;
import appointment.AppointmentSlot;
import user.UserId;

public interface AppointmentDatabaseApiPatient {
    public abstract ArrayList<Appointment> getPatientAppointments(UserId patientId);

    public abstract Appointment newAppointment(UserId patientId, UserId doctorId, AppointmentSlot slot);

    public abstract Appointment rescheduleAppointment(UserId patiendId, int id, AppointmentSlot slot);

    public abstract Appointment cancelAppointment(UserId patientId, int id);
}
