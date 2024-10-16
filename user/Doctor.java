package user;

import java.util.ArrayList;

import appointment.Appointment;
import appointment.AppointmentSlot;
import record.AppointmentOutcomeRecord;

public class Doctor extends Staff {
    private ArrayList<Appointment> appointments;
    private ArrayList<AppointmentSlot> slots;

    public Doctor(String id, String name, String role, String gender, int age) {
        super(id, name, role, gender, age);

        this.appointments = new ArrayList<Appointment>();
        this.slots = new ArrayList<AppointmentSlot>();
    }

    public void viewPersonalSchedule() {
        for (int i = 0; i < slots.size(); i++) {
            AppointmentSlot slot = this.slots.get(i);
            String available = slot.getAvailability() ? "Available" : "Not Available";
            System.out.println(slot.getDate() + ": " + available);
        }
    }

    public void viewUpcomingAppointments() {
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = this.appointments.get(i);
            if (appointment.getAppointmentStatus() == "Confirmed") System.out.println(appointment.getSlot().getDate());
        }
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
