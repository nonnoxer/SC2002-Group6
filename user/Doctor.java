package user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import appointment.Appointment;
import appointment.AppointmentSlot;
import appointment.Schedule;
import data.appointment.AppointmentDatabaseApiDoctor;
import medicine.Inventory;
import record.AppointmentOutcomeRecord;

public class Doctor extends Staff {
    private ArrayList<Patient> patients;
    private AppointmentDatabaseApiDoctor appointmentDb;
    private Schedule schedule;
    private Inventory inventory;

    public Doctor(String id, String name, String role, String gender, int age) {
        super(id, name, role, gender, age);

        this.patients = new ArrayList<Patient>();
        this.appointmentDb = null;
        this.schedule = null;
    }

    public void init(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setSchedule(LocalDate startDate, LocalDate endDate) {
        this.schedule = new Schedule(startDate, endDate);
    }

    public ArrayList<String> getPatientNames() {
        ArrayList<String> patientNames = new ArrayList<String>();
        for (Patient patient : patients) {
            patientNames.add(patient.getName());
        }
        return patientNames;
    }

    public Patient getPatientIndex(int index) {
        return this.patients.get(index);
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

    public ArrayList<Appointment> getUpcomingAppointments() {
        return this.appointmentDb.getDoctorAppointmentsUpcoming(this.id);
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

    public void setAvailability() {

    }
}
