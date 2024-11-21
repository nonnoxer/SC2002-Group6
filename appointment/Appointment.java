package appointment;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import data.CsvCompatible;
import medicine.Prescription;
import record.AppointmentOutcomeRecord;
import user.UserId;

/**
 * Represents a medical appointment between a patient and a doctor.
 * The appointment contains information such as the appointment status, scheduled time slot, 
 * and potentially the outcome record if the appointment has been completed.
 * It also provides methods for scheduling, rescheduling, and canceling the appointment.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class Appointment implements CsvCompatible {
    private int id;
    private UserId patientId, doctorId;
    private AppointmentStatus appointmentStatus;
    private AppointmentSlot slot;
    private AppointmentOutcomeRecord record;

    /**
     * Constructs an Appointment object with the given parameters.
     * The status is set to Pending, and the slot is scheduled.
     *
     * @param id the unique identifier of the appointment
     * @param patientId the user ID of the patient
     * @param doctorId the user ID of the doctor
     * @param slot the scheduled time slot of the appointment
     */
    public Appointment(int id, UserId patientId, UserId doctorId, AppointmentSlot slot) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentStatus = AppointmentStatus.Pending;
        this.slot = slot;
        this.record = null;

        slot.schedule();
    }

    /**
     * Constructs an Appointment object by parsing a CSV line. 
     * The line must contain exactly 13 elements (columns).
     *
     * @param line an array of strings representing a CSV line
     * @throws IOException if the line does not have the correct format
     */
    public Appointment(String[] line) throws IOException {
        if (line.length != 13) {
            String line_full = String.join(",", line);
            throw new IOException("Invalid line " + line_full + ": expected 11 elements.");
        }

        try {
            this.id = Integer.parseInt(line[0]);
        } catch (NumberFormatException e) {
            throw new IOException("Invalid line: expected " + line[0] + " to be an integer.");
        }
        try {
            this.patientId = new UserId(line[1]);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid line: expected " + line[1] + " to be a user ID.");
        }
        try {
            this.doctorId = new UserId(line[2]);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid line: expected " + line[2] + " to be a user ID.");
        }
        this.appointmentStatus = AppointmentStatus.valueOf(line[3]);

        this.slot = new AppointmentSlot(Arrays.copyOfRange(line, 4, 6));
        if (line[6].equals("")) {
            this.record = null;
        } else {
            this.record = new AppointmentOutcomeRecord(Arrays.copyOfRange(line, 6, 13));
            this.record.setAppointmentId(this.id);
            this.record.setSlot(this.slot);
        }
    }

    /**
     * Accepts or declines the appointment by the doctor.
     * If declined, the appointment status is set to Declined and the slot is canceled.
     *
     * @param accepted true if the appointment is accepted, false if declined
     */
    public void doctorAccept(boolean accepted) {
        if (!accepted) this.slot.cancel();
        this.appointmentStatus = accepted ? AppointmentStatus.Confirmed : AppointmentStatus.Declined;
    }

    /**
     * Marks the appointment as completed and sets the outcome record.
     *
     * @param record the outcome record associated with the appointment
     */
    public void complete(AppointmentOutcomeRecord record) {
        this.appointmentStatus = AppointmentStatus.Completed;
        this.record = record;
        record.setAppointmentId(this.id);
    }

    /**
     * Reschedules the appointment with a new time slot. 
     * The appointment status is reset to Pending and the new slot is scheduled.
     *
     * @param slot the new time slot for the appointment
     */
    public void patientReschedule(AppointmentSlot slot) {
        this.slot.cancel();
        this.slot = slot;
        this.appointmentStatus = AppointmentStatus.Pending;

        slot.schedule();
    }

    /**
     * Cancels the appointment. The appointment status is set to Canceled and the slot is canceled.
     */
    public void patientCancel() {
        this.slot.cancel();
        this.appointmentStatus = AppointmentStatus.Canceled;
    }

    /**
     * Returns the unique identifier for this appointment.
     *
     * @return the ID of the appointment
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the user ID of the patient associated with the appointment.
     *
     * @return the patient user ID
     */
    public UserId getPatientId() {
        return this.patientId;
    }

    /**
     * Returns the user ID of the doctor associated with the appointment.
     *
     * @return the doctor user ID
     */
    public UserId getDoctorId() {
        return this.doctorId;
    }

    /**
     * Returns the current status of the appointment.
     *
     * @return the status of the appointment
     */
    public AppointmentStatus getAppointmentStatus() {
        return this.appointmentStatus;
    }

    /**
     * Returns the scheduled time slot for the appointment.
     *
     * @return the time slot of the appointment
     */
    public AppointmentSlot getSlot() {
        return this.slot;
    }

    /**
     * Returns the outcome record for the appointment, if available.
     *
     * @return the appointment outcome record, or null if not available
     */
    public AppointmentOutcomeRecord getRecord() {
        return this.record;
    }

    /**
     * Converts the Appointment object to a CSV string format.
     * If the appointment has an outcome record, all related information will be included in the CSV string.
     *
     * @return a CSV string representing the appointment
     */
    public String toCsv() {
        if (this.record != null) return String.format(
            "%d,%s,%s,%s,%b,%s,%s,%s,%s,%s,%s,%s,%s",
            id, patientId, doctorId, appointmentStatus.toString(),
            slot.getAvailability(), slot.getDate(),
            record.getServiceType(), record.getConsultationNotes(), record.getPrescriptionStatus().toString(),
            record.getPrescription().stream().map(Prescription::getName).collect(Collectors.joining("::")),
            record.getPrescription().stream().map((prescription) -> String.valueOf(prescription.getQuantity())).collect(Collectors.joining("::")),
            record.getDiagnoses().stream().collect(Collectors.joining("::")),
            record.getTreatmentPlan().stream().collect(Collectors.joining("::"))
        );
        else return String.format(
            "%d,%s,%s,%s,%b,%s,,,,,,,",
            id, patientId, doctorId, appointmentStatus.toString(),
            slot.getAvailability(), slot.getDate()
        );
    }
}
