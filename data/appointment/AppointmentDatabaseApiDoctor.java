package data.appointment;

import java.util.ArrayList;

import appointment.Appointment;
import record.AppointmentOutcomeRecord;
import user.UserId;

/**
 * This interface defines the methods for a doctor to interact with the appointment database.
 * It provides functionality for retrieving a doctor's appointments, accepting or declining appointments, 
 * and setting the outcome of an appointment.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public interface AppointmentDatabaseApiDoctor {
    /**
     * Retrieves all appointments associated with a specific doctor.
     *
     * @param doctorId the unique identifier of the doctor whose appointments are to be retrieved
     * @return a list of Appointment objects associated with the specified doctor
     */
    public abstract ArrayList<Appointment> getDoctorAppointments(UserId doctorId);
    /**
     * Allows the doctor to accept or decline an appointment.
     *
     * @param doctorId the unique identifier of the doctor making the decision
     * @param id the ID of the appointment being accepted or declined
     * @param accepted a boolean value where true means the appointment is accepted, false means it is declined
     * @return the updated Appointment object after the decision is made
     */
    public abstract Appointment acceptAppointment(UserId doctorId, int id, boolean accepted);
    /**
     * Allows the doctor to set the outcome of an appointment, including consultation notes, diagnoses, and treatment plan.
     *
     * @param doctorId the unique identifier of the doctor who is setting the outcome
     * @param id the ID of the appointment whose outcome is being set
     * @param record the AppointmentOutcomeRecord object containing the details of the appointment outcome
     * @return the updated Appointment object with the outcome set
     */
    public abstract Appointment setOutcome(UserId doctorId, int id, AppointmentOutcomeRecord record);
}
