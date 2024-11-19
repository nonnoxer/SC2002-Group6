package user;

import appointment.Schedule;

public interface DoctorApiPatient {
    public abstract UserId getId();

    public abstract String getName();

    public abstract Schedule getPersonalSchedule();
}
