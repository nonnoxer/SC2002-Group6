package appointment;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import data.CsvCompatible;
import medicine.Prescription;
import record.AppointmentOutcomeRecord;
import user.UserId;

public class Appointment implements CsvCompatible {
    private int id;
    private UserId patientId, doctorId;
    private AppointmentStatus appointmentStatus;
    private AppointmentSlot slot;
    private AppointmentOutcomeRecord record;

    public Appointment(int id, UserId patientId, UserId doctorId, AppointmentSlot slot) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentStatus = AppointmentStatus.Pending;
        this.slot = slot;
        this.record = null;

        slot.schedule();
    }

    public Appointment(String[] line) throws IOException {
        if (line.length != 11) {
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
            this.record = new AppointmentOutcomeRecord(Arrays.copyOfRange(line, 6, 11));
            this.record.setAppointmentId(this.id);
            this.record.setSlot(this.slot);
        }
    }

    public void doctorAccept(boolean accepted) {
        if (!accepted) this.slot.cancel();
        this.appointmentStatus = accepted ? AppointmentStatus.Confirmed : AppointmentStatus.Declined;
    }

    public void complete(AppointmentOutcomeRecord record) {
        this.appointmentStatus = AppointmentStatus.Completed;
        this.record = record;
        record.setAppointmentId(this.id);
    }

    public void patientReschedule(AppointmentSlot slot) {
        this.slot.cancel();
        this.slot = slot;
        this.appointmentStatus = AppointmentStatus.Pending;

        slot.schedule();
    }

    public void patientCancel() {
        this.slot.cancel();
        this.appointmentStatus = AppointmentStatus.Canceled;
    }

    public int getId() {
        return this.id;
    }

    public UserId getPatientId() {
        return this.patientId;
    }

    public UserId getDoctorId() {
        return this.doctorId;
    }

    public AppointmentStatus getAppointmentStatus() {
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
            "%d,%s,%s,%s,%b,%s,%s,%s,%s,%s,%s",
            id, patientId, doctorId, appointmentStatus.toString(),
            slot.getAvailability(), slot.getDate(),
            record.getServiceType(), record.getConsultationNotes(), record.getPrescriptionStatus().toString(),
            record.getPrescription().stream().map(Prescription::getName).collect(Collectors.joining("::")),
            record.getPrescription().stream().map((prescription) -> String.valueOf(prescription.getQuantity())).collect(Collectors.joining("::"))
        );
        else return String.format(
            "%d,%s,%s,%s,%b,%s,,,,,",
            id, patientId, doctorId, appointmentStatus.toString(),
            slot.getAvailability(), slot.getDate()
        );
    }
}
