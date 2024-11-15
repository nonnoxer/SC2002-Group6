package appointment;

import java.util.stream.Collectors;

import data.CsvCompatible;
import medicine.Medicine;
import record.AppointmentOutcomeRecord;

public class Appointment implements CsvCompatible {
    private String patientId, doctorId, appointmentStatus;
    private AppointmentSlot slot;
    private AppointmentOutcomeRecord record;

    public Appointment(String patientId, String doctorId, AppointmentSlot slot) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentStatus = "Pending";
        this.slot = slot;
        this.record = null;
    }

    public Appointment(String patientId, String doctorId, String appointmentStatus, AppointmentSlot slot) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentStatus = appointmentStatus;
        this.slot = slot;
        this.record = null;
    }

    public void doctorAccept(boolean accepted) {
        if (accepted) this.slot.schedule();
        this.appointmentStatus = accepted ? "Confirmed" : "Declined";
    }

    public void complete(AppointmentOutcomeRecord record) {
        this.appointmentStatus = "Completed";
        this.record = record;
    }

    public void patientReschedule(AppointmentSlot slot) {
        this.slot.cancel();
        this.slot = slot;
    }

    public void patientCancel() {
        this.slot.cancel();
        this.appointmentStatus = "Canceled";
    }

    public String getPatientId() {
        return this.patientId;
    }

    public String getDoctorId() {
        return this.doctorId;
    }

    public String getAppointmentStatus() {
        return this.appointmentStatus;
    }

    public AppointmentSlot getSlot() {
        return this.slot;
    }

    public AppointmentOutcomeRecord getRecord() {
        return this.record;
    }

    public String toCsv() {
        if (this.record == null) return String.format(
            "%s,%s,%s,%b,%s,%s,%s,%s,%s",
            patientId, doctorId, appointmentStatus,
            slot.getAvailability(), slot.getDate(),
            record.getServiceType(), record.getConsultationNotes(), record.getPrescriptionStatus(),
            record.getPrescription().stream().map(Medicine::getName).collect(Collectors.joining("::"))
        );
        else return String.format(
            "%s,%s,%s,%b,%s,,,,",
            patientId, doctorId, appointmentStatus,
            slot.getAvailability(), slot.getDate()
        );
    }
}
