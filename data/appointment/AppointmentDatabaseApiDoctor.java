package data.appointment;

import java.util.ArrayList;

import appointment.Appointment;
import record.AppointmentOutcomeRecord;
import user.UserId;

/**
 * This interface defines the methods for how the doctor is to interact with the appointment database.
 * It provides functionality to retrieve a doctor's appointments, allowing to accept or decline appointments,
 * and set the outcome of an appointment.
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
     * Allow the doctor to accept or decline the appointment.
     * Appoinment is selected using the id
     *
     * @param doctorId is the unique identifier of the doctor that is making the decision
     * @param id is the ID of the appointment being accepted or declined
     * @param accepted is a boolean value where true means the appointment is accepted, false means it is declined
     * @return the updated Appointment object after accepted is set
     */
    public abstract Appointment acceptAppointment(UserId doctorId, int id, boolean accepted);
    /**
     * Allow the doctor to set the outcome of the appointment, including consultation notes, diagnoses, and treatment plan.
     *
     * @param doctorId the unique identifier of the doctor who is setting the outcome
     * @param id the ID of the appointment whose outcome is being set
     * @param record the AppointmentOutcomeRecord object containing the details of the appointment outcome
     * @return the updated Appointment object with the outcome set
     */
    public abstract Appointment setOutcome(UserId doctorId, int id, AppointmentOutcomeRecord record);
}
