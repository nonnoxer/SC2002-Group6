package user;

import appointment.Schedule;

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

    public Schedule getPersonalSchedule() {
        return this.doctor.getPersonalSchedule();
    }
}
