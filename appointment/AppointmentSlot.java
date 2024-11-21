package appointment;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Represents a specific time slot for an appointment, the scheduled date and availability status.
 * The availability status show whether the slot is available for scheduling or not.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class AppointmentSlot {
    private LocalDateTime date;
    private boolean availability;

    /**
     * Constructs an AppointmentSlot with the specified date and sets the availability to true.
     *
     * @param date the scheduled date and time of the appointment
     */
    public AppointmentSlot(LocalDateTime date) {
        this.date = date;
        this.availability = true;
    }

    /**
     * Constructs an AppointmentSlot from a CSV line. 
     * The first element is the availability status (true/false) and the second element is the date-time string.
     *
     * @param line an array of strings representing a CSV line containing the availability and date-time
     * @throws IOException if the line does not have the correct format or the date-time string is invalid
     */
    public AppointmentSlot(String[] line) throws IOException {
        this.availability = Boolean.parseBoolean(line[0]);

        try {
            this.date = LocalDateTime.parse(line[1]);
        } catch (DateTimeParseException e) {
            throw new IOException("Invalid line: expected " + line[1] + " to be a date-time string.");
        }
    }

    /**
     * Returns the scheduled date and time for this appointment slot.
     *
     * @return the date and time of the appointment
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Returns whether the appointment slot is available.
     *
     * @return true if the slot is available, if not false
     */
    public boolean getAvailability() {
        return this.availability;
    }

    /**
     * Marks the appointment slot as scheduled by setting the availability to false.
     */
    public void schedule() {
        this.availability = false;
    }

    /**
     * Marks the appointment slot as available by setting the availability to true.
     */
    public void cancel() {
        this.availability = true;
    }

    /**
     * Set the availability of the status of the appointment slot.
     *
     * @param availability is the new availability status of the slot
     */
    public void setAvailability(boolean availability){
        this.availability = availability;
    }
}
