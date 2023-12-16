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

        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            if (dayOffs.contains(i)) {
                Name member = dayOffMembers.poll();

                if (i != 1 && result.get(i - 1).equals(member)) {
                    Name switchedMember = dayOffMembers.poll();
                    dayOffMembers.addFirst(member);
                    result.put(i, switchedMember);
                    dayOffMembers.addLast(switchedMember);
                    continue;
                }
                dayOffMembers.addLast(member);
                result.put(i, member);
            }
            if (!dayOffs.contains(i)) {
                Name member = weekdayMembers.poll();

                if (i != 1 && result.get(i - 1).equals(member)) {
                    Name switchedMember = weekdayMembers.poll();
                    weekdayMembers.addFirst(member);
                    result.put(i, switchedMember);
                    weekdayMembers.addLast(switchedMember);
                    continue;
                }
                weekdayMembers.addLast(member);
                result.put(i, member);
            }
        }
        return result;
    }

    public List<Integer> getDayOffs(int month, DayOfWeek startDayOfWeek) {
        return DayOff.initializeMonthDayOff(month, startDayOfWeek);
    }
}
