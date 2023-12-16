package oncall.util;

import static oncall.constant.ErrorMessage.INVALID_INPUT_ERROR_MESSAGE;

import java.util.List;

public class InputValidator {
    private static final List<String> MONTH_FORMAT = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
    private static final List<String> DAY_OF_WEEK_FORMAT = List.of("월", "화", "수", "목", "금", "토", "일");

    public static void validateMonthFormat(String month) {
        if (!MONTH_FORMAT.contains(month)) {
            throw new IllegalArgumentException(INVALID_INPUT_ERROR_MESSAGE);
        }
    }

    public static void validateDayOfWeekFormat(String dayOfWeek) {
        if (!DAY_OF_WEEK_FORMAT.contains(dayOfWeek)) {
            throw new IllegalArgumentException(INVALID_INPUT_ERROR_MESSAGE);
        }
    }
}
