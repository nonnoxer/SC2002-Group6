package appointment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a schedule of appointment slots for a specific date range.
 * This class manages the availability of appointment slots on each day within the given start and end dates.
 * @author NATANAEL TAN TIONG OON
 * @version 1.0
 * @since 2024-11-21
 */
public class Schedule {
    private LocalDate startDate, endDate;
    private HashMap<LocalDate, ArrayList<AppointmentSlot>> calendar;

    /**
     * Constructs a Schedule for the given date range (from startDate to endDate).
     * It initializes the calendar with appointment slots for each day within the range.
     *
     * @param startDate the starting date of the schedule
     * @param endDate the ending date of the schedule
     */
    public Schedule(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;

        int numMonths = (int)startDate.until(endDate).toTotalMonths();
        this.calendar = new HashMap<>();

        for (int i = 0; i <= numMonths; i++) {
            LocalDate currentMonth = startDate.plusMonths(i);

            int startMonthDate = 1;
            int endMonthDate = currentMonth.lengthOfMonth();
            if (i == 0) startMonthDate = startDate.getDayOfMonth();
            if (i == numMonths) endMonthDate = endDate.getDayOfMonth();

            for (int j = startMonthDate; j <= endMonthDate; j++) {
                LocalDate date = currentMonth.withDayOfMonth(j);
                ArrayList<AppointmentSlot> slots = initSlots(date);
                calendar.put(date, slots);
            }
        }
    }

    /**
     * Prints the appointment slots for the specified month.
     * Optionally, only available slots can be displayed.
     *
     * @param month the month for which the schedule is printed
     * @param showOnlyAvailable whether to show only available slots
     */
    public void printMonth(LocalDate month, boolean showOnlyAvailable) {
        System.out.printf(
            "%s %d\n\n",
            month.getMonth().toString().substring(0, 3),
            month.getYear()
        );

        for (int i = 1; i <= 7; i++) {
            System.out.printf("%s  ", DayOfWeek.of(i).toString().substring(0, 3));
        }
        System.out.print("\n");

        LocalDate start = month.withDayOfMonth(1);
        int startDayOfWeek = start.getDayOfWeek().getValue();

        // initial day padding
        for (int i = 1; i < startDayOfWeek; i++) {
            System.out.print("     ");
        }

        int numDays = month.lengthOfMonth();
        for (int i = 1; i <= numDays; i++) {
            LocalDate day = month.withDayOfMonth(i);
            ArrayList<AppointmentSlot> slots = calendar.get(day);
            if (slots == null) {
                System.out.print("     ");
            } else {
                int numSlots = 0;
                for (AppointmentSlot slot : slots) {
                    if (!showOnlyAvailable || slot.getAvailability()) numSlots++;
                }
                if (numSlots > 0) {
                    System.out.print(String.format("%-5d", day.getDayOfMonth()));
                } else {
                    System.out.print("-    ");
                }
            }

            if ((startDayOfWeek + i - 1) % 7 == 0 && i != numDays) System.out.print("\n");
        }
        System.out.print("\n");
    }

    /**
     * Returns the list of appointment slots for the given date.
     *
     * @param date the date for which the slots are requested
     * @return a list of appointment slots for the specified date
     */
    public ArrayList<AppointmentSlot> getSlots(LocalDate date) {
        return this.calendar.get(date);
    }

    /**
     * Returns the list of available appointment slots for the given date.
     * Only the slots that are marked as available are returned.
     *
     * @param date the date for which the available slots are requested
     * @return a list of available appointment slots for the specified date, or null if no slots exist
     */
    public ArrayList<AppointmentSlot> getAvailableSlots(LocalDate date) {
        ArrayList<AppointmentSlot> allSlots = this.calendar.get(date);
        if (allSlots == null) return null;
        ArrayList<AppointmentSlot> slots = new ArrayList<>();
        for (AppointmentSlot slot : allSlots) {
            if (slot.getAvailability()) slots.add(slot);
        }
        return slots;
    }

    /**
     * Initializes the appointment slots for a specific day.
     * The slots are created based on the day of the week and predefined working hours.
     * 
     * Weekdays have slots from 08:00-13:00 and 14:00-16:30.
     * Saturdays have slots from 08:00-12:30.
     * Sundays have no available slots.
     *
     * @param day the date for which the appointment slots are to be initialized
     * @return a list of appointment slots for the specified day
     */
    private ArrayList<AppointmentSlot> initSlots(LocalDate day) {
        ArrayList<AppointmentSlot> slots = new ArrayList<AppointmentSlot>();

        // 0800-1300, and 1400-1630 for weekdays
        // 0800-1230 for saturday
        // no slot on sunday

        int lastSlot;
        switch (day.getDayOfWeek()) {
            case SUNDAY:
                lastSlot = 0;
                break;
            case SATURDAY:
                lastSlot = 9;
                break;
            default:
                lastSlot = 17;
                break;
        }

        for (int i = 0; i < lastSlot; i++) {
            int hour = i / 2 + 8;
            int minute = i % 2 * 30;
            if (hour == 13) continue;
            LocalDateTime time = day.atTime(hour, minute);
            slots.add(new AppointmentSlot(time));
        }

        return slots;
    }


    /**
     * Returns the start date of the schedule.
     *
     * @return the start date of the schedule
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Returns the end date of the schedule.
     *
     * @return the end date of the schedule
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }
}
