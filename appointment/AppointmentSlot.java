package appointment;

public class AppointmentSlot {
    private String date;
    private boolean availability;

    public AppointmentSlot(String date) {
        this.date = date;
        this.availability = true;
    }

    public String getDate() {
        return this.date;
    }

    public boolean getAvailability() {
        return this.availability;
    }

    public void schedule() {
        this.availability = false;
    }

    public void cancel() {
        this.availability = true;
    }
}
