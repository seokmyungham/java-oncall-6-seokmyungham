package oncall.domain;

import java.util.Arrays;

public enum DayOfWeek {
    MONDAY("월", 1),
    TUESDAY("화", 2),
    WEDNESDAY("수", 3),
    THURSDAY("목", 4),
    FRIDAY("금", 5),
    SATURDAY("토", 6),
    SUNDAY("일", 7);

    private final String name;
    private final int code;

    DayOfWeek(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static DayOfWeek getDayOfWeekByName(String name) {
        return Arrays.stream(DayOfWeek.values())
                .filter(dayOfWeek -> dayOfWeek.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    public static String getNameByCode(int code) {
        return Arrays.stream(DayOfWeek.values())
                .filter(dayOfWeek -> dayOfWeek.code == code)
                .findFirst()
                .map(DayOfWeek::getName)
                .orElse(null);
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
