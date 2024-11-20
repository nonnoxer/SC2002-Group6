package appointment;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class AppointmentSlot {
    private LocalDateTime date;
    private boolean availability;

    public AppointmentSlot(LocalDateTime date) {
        this.date = date;
        this.availability = true;
    }

    public AppointmentSlot(String[] line) throws IOException {
        this.availability = Boolean.parseBoolean(line[0]);

        try {
            this.date = LocalDateTime.parse(line[1]);
        } catch (DateTimeParseException e) {
            throw new IOException("Invalid line: expected " + line[1] + " to be a date-time string.");
        }
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

    public void setAvailability(boolean availability){
        this.availability = availability;
    }
}
