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
import medicine.Inventory;
import record.AppointmentOutcomeRecord;

public class Doctor extends Staff implements DoctorApiPatient {
    private UserDatabaseApiDoctor userDb;
    private AppointmentDatabaseApiDoctor appointmentDb;
    private Schedule schedule;
    private Inventory inventory;

    public Doctor(UserId id, String name, Role role, String gender, int age) {
        super(id, name, role, gender, age);

        this.userDb = null;
        this.appointmentDb = null;
        this.schedule = null;
    }

    public void init(UserDatabaseApiDoctor userDb, Inventory inventory) {
        this.userDb = userDb;
        this.inventory = inventory;
    }

    public void setSchedule(LocalDate startDate, LocalDate endDate) {
        this.schedule = new Schedule(startDate, endDate);
    }

    public ArrayList<Patient> getPatients() {
        return this.userDb.getPatients(this.appointmentDb.getDoctorAppointments(this.id));
    }

    public Patient getPatientIndex(UserId patientId) {
        return this.userDb.findPatientId(patientId);
    }

    public Schedule getPersonalSchedule() {
        return this.schedule;
    }

    public void setAppointmentDb(AppointmentDatabaseApiDoctor appointmentDb) {
        this.appointmentDb = appointmentDb;

        // update schedule
        ArrayList<Appointment> appointments = this.getAppointments();
        for (Appointment appointment : appointments) {
            LocalDateTime dateTime = appointment.getSlot().getDate();
            ArrayList<AppointmentSlot> slots = this.schedule.getSlots(dateTime.toLocalDate());
            for (AppointmentSlot slot : slots) {
                if (slot.getDate().equals(dateTime)) {
                    appointment.setSlot(slot);
                    break;
                }
            }
        }
    }

    public ArrayList<Appointment> getAppointments() {
        return this.appointmentDb.getDoctorAppointments(this.id);
    }

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

    public void acceptRequest(int appointmentId, boolean accepted) {
        this.appointmentDb.acceptAppointment(this.id, appointmentId, accepted);
    }

    public void recordOutcome(int appointmentId, AppointmentOutcomeRecord record) {
        this.appointmentDb.setOutcome(this.id, appointmentId, record);
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
}
