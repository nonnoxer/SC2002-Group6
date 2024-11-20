package data.appointment;

import java.util.ArrayList;

import record.AppointmentOutcomeRecord;

public interface AppointmentDatabaseApiPharmacist {
    public abstract ArrayList<AppointmentOutcomeRecord> getRecords();
    public abstract AppointmentOutcomeRecord dispensePrescription(int id);
}
