package oncall.domain;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum DayOff {
    JANUARY(1, List.of(1)),
    FEBRUARY(2, List.of()),
    MARCH(3, List.of(1)),
    APRIL(4, List.of()),
    MAY(5, List.of(5)),
    JUNE(6, List.of()),
    JULY(7, List.of()),
    AUGUST(8, List.of()),
    SEPTEMBER(9, List.of()),
    OCTOBER(10, List.of(3, 9)),
    NOVEMBER(11, List.of()),
    DECEMBER(12, List.of(25));

    private int month;
    private List<Integer> days;

    DayOff(int month, List<Integer> days) {
        this.month = month;
        this.days = days;
    }

    public static List<Integer> initializeMonthDayOff(int month, DayOfWeek startDayOfMonth) {
        List<Integer> result = new ArrayList<>();

        int startDay = startDayOfMonth.getCode();

        YearMonth yearMonth = YearMonth.of(2023, month);
        int monthLength = yearMonth.lengthOfMonth();

        if (startDay == DayOfWeek.SATURDAY.getCode() || startDay == DayOfWeek.SUNDAY.getCode()) {
            result.add(1);
        }
        for (int day = 2; day <= monthLength; day++) {
            startDay = (startDay % 7) + 1;
            if (startDay == DayOfWeek.SATURDAY.getCode() || startDay == DayOfWeek.SUNDAY.getCode()) {
                result.add(day);
            }
        }

        result.addAll(getDaysByMonth(month));
        Collections.sort(result);
        return result;
    }

    public static List<Integer> getDaysByMonth(int month) {
        return Arrays.stream(DayOff.values())
                .filter(dayOff -> dayOff.month == month)
                .findFirst()
                .map(DayOff::getDays)
                .orElse(null);
    }

    public static DayOff getDayOffByMonth(int month) {
        return Arrays.stream(DayOff.values())
                .filter(dayOff -> dayOff.month == month)
                .findFirst()
                .orElse(null);
    }

    public static boolean containsDay(DayOff dayOff, int day) {
        if (dayOff.days.contains(day)) {
            return true;
        }
        return false;
    }

    public List<Integer> getDays() {
        return days;
    }
}

