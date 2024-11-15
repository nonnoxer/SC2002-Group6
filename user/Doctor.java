package user;

import java.time.LocalDate;
import java.util.ArrayList;

import appointment.Appointment;
import appointment.Schedule;
import record.AppointmentOutcomeRecord;

public class Doctor extends Staff {
    private ArrayList<Patient> patients;
    private ArrayList<Appointment> appointments;
    private Schedule schedule;

    public Doctor(String id, String name, String role, String gender, int age) {
        super(id, name, role, gender, age);

        this.patients = new ArrayList<Patient>();
        this.appointments = new ArrayList<Appointment>();
        this.schedule = null;
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

    public ArrayList<Appointment> getUpcomingAppointments() {
        ArrayList<Appointment> upcoming = new ArrayList<Appointment>();
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getAppointmentStatus() == "Confirmed") upcoming.add(appointment);
        }
        return upcoming;
    }

    public void setAvailability() {

    }

    public void acceptRequest(Appointment request, boolean accepted) {
        request.doctorAccept(accepted);
    }

    public void recordOutcome(Appointment appointment, AppointmentOutcomeRecord record) {
        appointment.complete(record);
    }
}
