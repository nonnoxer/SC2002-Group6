package appointment;

/**
 * Enum representing the possible statuses of an appointment.
 * This enum is used to track the current state of an appointment throughout its lifecycle.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public enum AppointmentStatus {
    Pending,
    Confirmed,
    Declined,
    Canceled,
    Completed
}
