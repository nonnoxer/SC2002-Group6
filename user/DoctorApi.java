package user;

import java.util.ArrayList;

import appointment.AppointmentSlot;

public class DoctorApi {
    private Doctor doctor;

    public DoctorApi(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getId() {
        return this.doctor.getID();
    }

    public String getName() {
        return this.doctor.getName();
    }

    public ArrayList<AppointmentSlot> getPersonalSchedule() {
        return this.doctor.getPersonalSchedule();
    }
}
