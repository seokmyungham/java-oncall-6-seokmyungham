package oncall.view;

import java.util.Map;
import oncall.domain.DayOfWeek;
import oncall.domain.DayOff;
import oncall.domain.Name;

public class OutputView {

    public void printWorkSchedule(int month, Map<Integer, Name> workSchedule, int dayOfWeekCode) {
        for (int day : workSchedule.keySet()) {
            dayOfWeekCode = loopDayOfWeekCode(dayOfWeekCode);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(month + "월 " + day + "일 ");
            if (isDayOff(month, day)) {
                stringBuilder.append(DayOfWeek.getNameByCode(dayOfWeekCode++) + "(휴일) ");
            }
            if (!isDayOff(month, day)) {
                stringBuilder.append(DayOfWeek.getNameByCode(dayOfWeekCode++) + " ");
            }
            stringBuilder.append(workSchedule.get(day).getName());
            System.out.println(stringBuilder.toString());
        }
    }

    private int loopDayOfWeekCode(int dayOfWeekCode) {
        if (dayOfWeekCode > 7) {
            dayOfWeekCode = 1;
        }
        return dayOfWeekCode;
    }

    private boolean isDayOff(int month, int day) {
        return DayOff.containsDay(DayOff.getDayOffByMonth(month), day);
    }
}
