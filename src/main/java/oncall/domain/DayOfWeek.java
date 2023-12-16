package oncall.domain;

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

    public int getCode() {
        return code;
    }
}
