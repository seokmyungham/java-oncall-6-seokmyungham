package oncall.domain;

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
}

