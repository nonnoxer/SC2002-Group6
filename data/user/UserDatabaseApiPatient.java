package data.user;

import java.util.ArrayList;

import user.DoctorApiPatient;
import user.UserId;

public interface UserDatabaseApiPatient {
    public abstract ArrayList<DoctorApiPatient> getDoctors();
    public abstract DoctorApiPatient findDoctorId(UserId doctorId);
    public abstract void updatePatient();
}
