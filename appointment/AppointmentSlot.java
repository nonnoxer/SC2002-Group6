package appointment;

import java.time.LocalDateTime;

public class AppointmentSlot {
    private LocalDateTime date;
    private boolean availability;

    public AppointmentSlot(LocalDateTime date) {
        this.date = date;
        this.availability = true;
    }

    public LocalDateTime getDate() {
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
