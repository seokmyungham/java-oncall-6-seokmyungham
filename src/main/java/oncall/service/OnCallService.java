package oncall.service;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import oncall.domain.DayOfWeek;
import oncall.domain.DayOff;
import oncall.domain.Name;

public class OnCallService {
    public Map<Integer, Name> service(LinkedList<Name> weekdayMembers, LinkedList<Name> dayOffMembers, List<Integer> dayOffs, int month) {
        Map<Integer, Name> result = new HashMap<>();
        YearMonth yearMonth = YearMonth.of(2023, month);
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            boolean isDayOff = dayOffs.contains(day);
            if (isDayOff) {
                dayOffProcess(day, dayOffMembers, result);
            }
            if (!isDayOff) {
                weekdayProcess(day, weekdayMembers, result);
            }
        }
        return result;
    }

    private void dayOffProcess(int day, LinkedList<Name> dayOffMembers, Map<Integer, Name> result) {
            Name member = dayOffMembers.poll();
            if (isWorkedJustBefore(day, result, member)) {
                switchMembers(dayOffMembers, member, result, day);
                return;
            }
            dayOffMembers.addLast(member);
            result.put(day, member);
    }

    private void weekdayProcess(int day, LinkedList<Name> weekdayMembers, Map<Integer, Name> result) {
            Name member = weekdayMembers.poll();
            if (isWorkedJustBefore(day, result, member)) {
                switchMembers(weekdayMembers, member, result, day);
                return;
            }
            weekdayMembers.addLast(member);
            result.put(day, member);
    }

    private boolean isWorkedJustBefore(int day, Map<Integer, Name> result, Name member) {
        return day != 1 && result.get(day - 1).equals(member);
    }

    private void switchMembers(LinkedList<Name> members, Name member, Map<Integer, Name> result, int day) {
        Name switchedMember = members.poll();
        members.addFirst(member);
        result.put(day, switchedMember);
        members.addLast(switchedMember);
    }

    public List<Integer> getDayOffs(int month, DayOfWeek startDayOfWeek) {
        return DayOff.initializeMonthDayOff(month, startDayOfWeek);
    }
}
