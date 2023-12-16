package oncall.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import oncall.domain.DayOfWeek;
import oncall.domain.Name;
import oncall.service.OnCallService;
import oncall.util.InputParser;
import oncall.util.InputValidator;
import oncall.view.InputView;
import oncall.view.OutputView;

public class OnCallController {
    public static final String WEEKDAY_CODE = "WEEKDAY";
    public static final String DAY_OFF_CODE = "DAYOFF";

    private final OnCallService onCallService;
    private final InputView inputView;
    private final OutputView outputView;

    public OnCallController() {
        this.onCallService = new OnCallService();
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void process() {
        List<String> monthAndDayOfWeek = getMonthAndDayOfWeek();

        int month = Integer.parseInt(monthAndDayOfWeek.get(0));
        DayOfWeek startDayOfWeek = DayOfWeek.getDayOfWeekByName(monthAndDayOfWeek.get(1));

        Map<String, List<String>> totalMembers = getTotalMembers();
        LinkedList<Name> weekdayMembers = getMembersByCode(totalMembers, WEEKDAY_CODE);
        LinkedList<Name> dayOffMembers = getMembersByCode(totalMembers, DAY_OFF_CODE);

        List<Integer> dayOffs = onCallService.getDayOffs(month, startDayOfWeek);

        Map<Integer, Name> workSchedule = onCallService.service(weekdayMembers, dayOffMembers, dayOffs, month);
        outputView.printWorkSchedule(month, workSchedule, startDayOfWeek.getCode());
    }

    private List<String> getMonthAndDayOfWeek() {
        try {
            List<String> monthAndDayOfWeek = InputParser.parseInputByDelimiter(inputView.inputMonthAndDayOfWeek(), ",");
            InputValidator.validateMonthFormat(monthAndDayOfWeek.get(0));
            InputValidator.validateDayOfWeekFormat(monthAndDayOfWeek.get(1));
            return monthAndDayOfWeek;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getMonthAndDayOfWeek();
        }
    }

    private Map<String, List<String>> getTotalMembers() {
        try {
            List<String> weekdayMembers = getAndValidateWeekdayMembers();
            List<String> dayOffMembers = getAndValidateDayOffMembers();
            Map<String, List<String>> totalMembers = new HashMap<>();
            totalMembers.put(WEEKDAY_CODE, weekdayMembers);
            totalMembers.put(DAY_OFF_CODE, dayOffMembers);
            return totalMembers;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getTotalMembers();
        }
    }

    private LinkedList<Name> getMembersByCode(Map<String, List<String>> totalMembers, String dayOffCode) {
        LinkedList<Name> dayOffMembers = new LinkedList<>();
        for (String name : totalMembers.get(dayOffCode)) {
            dayOffMembers.add(new Name(name));
        }
        return dayOffMembers;
    }

    private List<String> getAndValidateDayOffMembers() {
        List<String> dayOffMembers = InputParser.parseInputByDelimiter(inputView.inputDayOffMembers(), ",");
        InputValidator.validateMembersSize(dayOffMembers);
        InputValidator.validateDuplicatedMember(dayOffMembers);
        return dayOffMembers;
    }

    private List<String> getAndValidateWeekdayMembers() {
        List<String> weekdayMembers = InputParser.parseInputByDelimiter(inputView.inputWeekdayMembers(), ",");
        InputValidator.validateMembersSize(weekdayMembers);
        InputValidator.validateDuplicatedMember(weekdayMembers);
        return weekdayMembers;
    }
}
