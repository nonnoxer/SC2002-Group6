package user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import appointment.Appointment;
import appointment.AppointmentSlot;
import appointment.AppointmentStatus;
import appointment.Schedule;
import data.appointment.AppointmentDatabaseApiDoctor;
import data.user.UserDatabaseApiDoctor;
import medicine.InventoryApiDoctor;
import medicine.Medicine;
import record.AppointmentOutcomeRecord;

/**
 * Represents a Doctor in the system, who is responsible for managing their schedule,
 * handling appointments, and interacting with patients. The Doctor can also manage
 * inventory related to medical supplies and medications.
 * The Doctor implements the DoctorApiPatient interface to handle patient-related operations.
 * 
 * @author LOW KAN YUI (LIU GENGRUI)
 * @version 1.0
 * @since 2024-11-21
 */
public class Doctor extends Staff implements DoctorApiPatient {
    private UserDatabaseApiDoctor userDb;
    private AppointmentDatabaseApiDoctor appointmentDb;
    private Schedule schedule;
    private InventoryApiDoctor inventory;

    /**
     * Constructs a Doctor object with the given parameters and initializes the userDb and appointmentDb to null.
     *
     * @param id      The user ID of the doctor.
     * @param name    The name of the doctor.
     * @param role    The role of the doctor.
     * @param gender  The gender of the doctor.
     * @param age     The age of the doctor.
     */
    public Doctor(UserId id, String name, Role role, String gender, int age) {
        super(id, name, role, gender, age);

        this.userDb = null;
        this.appointmentDb = null;
        this.schedule = null;
    }

    /**
     * Initializes the Doctor object with the necessary database references for managing patients
     * and the inventory.
     *
     * @param userDb   The User Database API Doctor.
     * @param inventory The Inventory API Doctor.
     */
    public void init(UserDatabaseApiDoctor userDb, InventoryApiDoctor inventory) {
        this.userDb = userDb;
        this.inventory = inventory;
    }

    /**
     * Sets the doctor's schedule for a given start date and end date.
     *
     * @param startDate The start date of the doctor's schedule.
     * @param endDate   The end date of the doctor's schedule.
     */
    public void setSchedule(LocalDate startDate, LocalDate endDate) {
        this.schedule = new Schedule(startDate, endDate);
    }

    /**
     * Retrieves a list of all patients assigned to the doctor based on their appointments.
     *
     * @return A list of patients assigned to the doctor.
     */
    public ArrayList<Patient> getPatients() {
        return this.userDb.getPatients(this.appointmentDb.getDoctorAppointments(this.id));
    }

    /**
     * Retrieves the patient information for a specific patient based on their ID.
     *
     * @param patientId The unique identifier of the patient.
     * @return The patient corresponding to the given ID.
     */
    public Patient getPatientIndex(UserId patientId) {
        return this.userDb.findPatientId(patientId);
    }

    /**
     * Retrieves the doctor's personal schedule.
     *
     * @return The doctor's schedule.
     */
    public Schedule getPersonalSchedule() {
        return this.schedule;
    }

    /**
     * Sets the appointment database for the doctor and updates the doctor's schedule.
     *
     * @param appointmentDb The Appointment Database API Doctor.
     */
    public void setAppointmentDb(AppointmentDatabaseApiDoctor appointmentDb) {
        this.appointmentDb = appointmentDb;

        // update schedule
        ArrayList<Appointment> appointments = this.getAppointments();
        for (Appointment appointment : appointments) {
            LocalDateTime dateTime = appointment.getSlot().getDate();
            ArrayList<AppointmentSlot> slots = this.schedule.getSlots(dateTime.toLocalDate());
            for (int i = 0; i < slots.size(); i++) {
                if (slots.get(i).getDate().equals(dateTime)) {
                    slots.set(i, appointment.getSlot());
                    break;
                }
            }
        }
    }

    /**
     * Retrieves a list of all appointments for the doctor.
     *
     * @return A list of appointments for the doctor.
     */
    public ArrayList<Appointment> getAppointments() {
        return this.appointmentDb.getDoctorAppointments(this.id);
    }

    /**
     * Retrieves a list of all pending appointments for the doctor.
     *
     * @return A list of pending appointments for the doctor.
     */
    public ArrayList<Appointment> getPendingAppointments() {
        ArrayList<Appointment> result = new ArrayList<>();
        for (Appointment appointment : this.appointmentDb.getDoctorAppointments(this.id)) {
            AppointmentStatus status = appointment.getAppointmentStatus();
            if (status.equals(AppointmentStatus.Pending)) {
                result.add(appointment);
            }
        }
        return result;
    }

    /**
     * Retrieves a list of all upcoming (confirmed) appointments for the doctor.
     *
     * @return A list of upcoming (confirmed) appointments for the doctor.
     */
    public ArrayList<Appointment> getUpcomingAppointments() {
        ArrayList<Appointment> result = new ArrayList<>();
        for (Appointment appointment : this.appointmentDb.getDoctorAppointments(this.id)) {
            AppointmentStatus status = appointment.getAppointmentStatus();
            if (status.equals(AppointmentStatus.Confirmed)) {
                result.add(appointment);
            }
        }
        return result;
    }

    /**
     * Accepts or rejects an appointment request.
     *
     * @param appointmentId The unique identifier of the appointment.
     * @param accepted      A boolean indicating whether the appointment is accepted (true) or rejected (false).
     */
    public void acceptRequest(int appointmentId, boolean accepted) {
        this.appointmentDb.acceptAppointment(this.id, appointmentId, accepted);
    }

    /**
     * Records the outcome of a completed appointment, including any diagnoses, treatment plans, or prescriptions.
     *
     * @param appointmentId The unique identifier of the appointment.
     * @param record        The outcome record of the appointment.
     */
    public void recordOutcome(int appointmentId, AppointmentOutcomeRecord record) {
        this.appointmentDb.setOutcome(this.id, appointmentId, record);
    }
    
    /**
     * Retrieves and returns the current inventory of medicines available to the doctor.
     *
     * @return A list of medicines in the doctor's inventory.
     */
    public ArrayList<Medicine> getInventory() {
        return this.inventory.getInventory();
    }
}
