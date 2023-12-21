package oncall.service;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oncall.domain.Crews;
import oncall.domain.DayOfWeek;
import oncall.domain.DayOff;
import oncall.domain.Name;

public class OnCallService {
    public Map<Integer, Name> service(Crews weekdayMembers, Crews dayOffMembers, List<Integer> dayOffs, int month) {
        Map<Integer, Name> workSchedule = new HashMap<>();
        YearMonth yearMonth = YearMonth.of(2023, month);
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            boolean isDayOff = dayOffs.contains(day);
            if (isDayOff) {
                createSchedule(day, dayOffMembers, workSchedule);
            }
            if (!isDayOff) {
                createSchedule(day, weekdayMembers, workSchedule);
            }
        }
        return workSchedule;
    }

    private void createSchedule(int day, Crews members, Map<Integer, Name> workSchedule) {
        Name member = members.getCrews().poll();
        if (isWorkedJustBefore(day, workSchedule, member)) {
            switchMembers(members, member, workSchedule, day);
            return;
        }
        members.getCrews().addLast(member);
        workSchedule.put(day, member);
    }

    private boolean isWorkedJustBefore(int day, Map<Integer, Name> result, Name member) {
        return day != 1 && result.get(day - 1).equals(member);
    }

    private void switchMembers(Crews members, Name member, Map<Integer, Name> workSchedule, int day) {
        Name switchedMember = members.getCrews().poll();
        members.getCrews().addFirst(member);
        workSchedule.put(day, switchedMember);
        members.getCrews().addLast(switchedMember);
    }

    public List<Integer> getDayOffs(int month, DayOfWeek startDayOfWeek) {
        return DayOff.initializeMonthDayOff(month, startDayOfWeek);
    }
}
