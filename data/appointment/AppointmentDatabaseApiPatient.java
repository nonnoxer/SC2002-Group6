package data.appointment;

import java.util.ArrayList;

import appointment.Appointment;
import appointment.AppointmentSlot;
import user.UserId;

/**
 * This interface defines the methods for a patient to interact with the appointment database.
 * It provides functionality for retrieving a patient's appointments, creating new appointments, 
 * rescheduling appointments, and canceling appointments.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public interface AppointmentDatabaseApiPatient {
    /**
     * Retrieves all appointments associated with a specific patient.
     *
     * @param patientId the unique identifier of the patient whose appointments are to be retrieved
     * @return a list of Appointment objects associated with the specified patient
     */
    public abstract ArrayList<Appointment> getPatientAppointments(UserId patientId);

    /**
     * Allows a patient to create a new appointment with a specified doctor and appointment slot.
     *
     * @param patientId the unique identifier of the patient scheduling the appointment
     * @param doctorId the unique identifier of the doctor with whom the appointment is being scheduled
     * @param slot the appointment slot where the patient wants to schedule the appointment
     * @return the newly created Appointment object
     */
    public abstract Appointment newAppointment(UserId patientId, UserId doctorId, AppointmentSlot slot);

    /**
     * Allows a patient to reschedule an existing appointment to a new slot.
     *
     * @param patientId the unique identifier of the patient rescheduling the appointment
     * @param id the ID of the appointment being rescheduled
     * @param slot the new appointment slot for the rescheduled appointment
     * @return the updated Appointment object after rescheduling
     */
    public abstract Appointment rescheduleAppointment(UserId patiendId, int id, AppointmentSlot slot);

    /**
     * Allows a patient to cancel an existing appointment.
     *
     * @param patientId the unique identifier of the patient canceling the appointment
     * @param id the ID of the appointment being canceled
     * @return the updated Appointment object after cancellation
     */
    public abstract Appointment cancelAppointment(UserId patientId, int id);
}
