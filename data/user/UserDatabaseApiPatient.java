package data.user;

import java.util.ArrayList;

import user.DoctorApiPatient;

public interface UserDatabaseApiPatient {
    public abstract ArrayList<DoctorApiPatient> getDoctors();
    public abstract DoctorApiPatient findDoctorId(String doctorId);
}
