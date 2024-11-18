package appointment;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import data.CsvCompatible;
import medicine.Medicine;
import record.AppointmentOutcomeRecord;

public class Appointment implements CsvCompatible {
    private int id;
    private String patientId, doctorId, appointmentStatus;
    private AppointmentSlot slot;
    private AppointmentOutcomeRecord record;

    public Appointment(int id, String patientId, String doctorId, AppointmentSlot slot) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentStatus = "Pending";
        this.slot = slot;
        this.record = null;

        slot.schedule();
    }

    public Appointment(String[] line) throws IOException {
        if (line.length != 10) {
            String line_full = String.join(",", line);
            throw new IOException("Invalid line " + line_full + ": expected 10 elements.");
        }

        try {
            this.id = Integer.parseInt(line[0]);
        } catch (NumberFormatException e) {
            throw new IOException("Invalid line: expected " + line[0] + " to be an integer.");
        }
        this.patientId = line[1];
        this.doctorId = line[2];
        this.appointmentStatus = line[3];

        this.slot = new AppointmentSlot(Arrays.copyOfRange(line, 4, 6));
        if (line[6].equals("")) {
            this.record = null;
        } else {
            this.record = new AppointmentOutcomeRecord(Arrays.copyOfRange(line, 6, 10));
        }
    }

    public void doctorAccept(boolean accepted) {
        if (!accepted) this.slot.cancel();
        this.appointmentStatus = accepted ? "Confirmed" : "Declined";
    }

    public void complete(AppointmentOutcomeRecord record) {
        this.appointmentStatus = "Completed";
        this.record = record;
    }

    public void patientReschedule(AppointmentSlot slot) {
        this.slot.cancel();
        this.slot = slot;

        slot.schedule();
    }

    public void patientCancel() {
        this.slot.cancel();
        this.appointmentStatus = "Canceled";
    }

    public void setSlot(AppointmentSlot slot) {
        this.slot.cancel();
        this.slot = slot;
        slot.schedule();
    }

    public int getId() {
        return this.id;
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
        if (this.record != null) return String.format(
            "%d,%s,%s,%s,%b,%s,%s,%s,%s,%s",
            id, patientId, doctorId, appointmentStatus,
            slot.getAvailability(), slot.getDate(),
            record.getServiceType(), record.getConsultationNotes(), record.getPrescriptionStatus(),
            record.getPrescription().stream().map(Medicine::getName).collect(Collectors.joining("::"))
        );
        else return String.format(
            "%d,%s,%s,%s,%b,%s,,,,",
            id, patientId, doctorId, appointmentStatus,
            slot.getAvailability(), slot.getDate()
        );
    }
}
