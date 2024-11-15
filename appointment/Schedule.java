package appointment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Schedule {
    private LocalDate startDate, endDate;
    private HashMap<LocalDate, ArrayList<AppointmentSlot>> calendar;

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

    public ArrayList<AppointmentSlot> getSlots(LocalDate date) {
        return this.calendar.get(date);
    }

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

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }
}
