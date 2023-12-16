package oncall.controller;

import java.util.ArrayList;
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

        Map<String, List<String>> memberNamesAndTurn = getMembersAndTurn();
        LinkedList<Name> weekdayMembers = new LinkedList<>();
        for (String name : memberNamesAndTurn.get("WEEKDAY")) {
            weekdayMembers.add(new Name(name));
        }

        LinkedList<Name> dayOffMembers = new LinkedList<>();
        for (String name : memberNamesAndTurn.get("DAYOFF")) {
            dayOffMembers.add(new Name(name));
        }
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

    private Map<String, List<String>> getMembersAndTurn() {
        try {
            List<String> weekDayMembers = InputParser.parseInputByDelimiter(inputView.inputWeekdayMembers(), ",");
            InputValidator.validateMembersSize(weekDayMembers);
            InputValidator.validateDuplicatedMember(weekDayMembers);
            List<String> dayOffMembers = InputParser.parseInputByDelimiter(inputView.inputDayOffMembers(), ",");
            InputValidator.validateMembersSize(dayOffMembers);
            InputValidator.validateDuplicatedMember(dayOffMembers);
            Map<String, List<String>> members = new HashMap<>();
            members.put("WEEKDAY", weekDayMembers);
            members.put("DAYOFF", dayOffMembers);
            return members;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getMembersAndTurn();
        }
    }
}
