package oncall.controller;

import static oncall.constant.CodeConstant.DAY_OFF_CODE;
import static oncall.constant.CodeConstant.DELIMITER_COMMA;
import static oncall.constant.CodeConstant.INDEX_ONE;
import static oncall.constant.CodeConstant.INDEX_ZERO;
import static oncall.constant.CodeConstant.WEEKDAY_CODE;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import oncall.constant.CodeConstant;
import oncall.domain.Crews;
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

        int month = Integer.parseInt(monthAndDayOfWeek.get(INDEX_ZERO));
        DayOfWeek startDayOfWeek = DayOfWeek.getDayOfWeekByName(monthAndDayOfWeek.get(INDEX_ONE));

        Map<String, Crews> totalMembers = getTotalMembers();
        Crews weekdayMembers = totalMembers.get(WEEKDAY_CODE);
        Crews dayOffMembers = totalMembers.get(DAY_OFF_CODE);

        List<Integer> dayOffs = onCallService.getDayOffs(month, startDayOfWeek);

        Map<Integer, Name> workSchedule = onCallService.service(weekdayMembers, dayOffMembers, dayOffs, month);
        outputView.printWorkSchedule(month, workSchedule, startDayOfWeek.getCode(), dayOffs);
    }

    private List<String> getMonthAndDayOfWeek() {
        try {
            List<String> monthAndDayOfWeek = InputParser.parseInputByDelimiter(inputView.inputMonthAndDayOfWeek(), DELIMITER_COMMA);
            InputValidator.validateMonthFormat(monthAndDayOfWeek.get(INDEX_ZERO));
            InputValidator.validateDayOfWeekFormat(monthAndDayOfWeek.get(INDEX_ONE));
            return monthAndDayOfWeek;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getMonthAndDayOfWeek();
        }
    }

    private Map<String, Crews> getTotalMembers() {
        try {
            Crews weekdayMembers = getAndValidateWeekdayMembers();
            Crews dayOffMembers = getAndValidateDayOffMembers();
            Map<String, Crews> totalMembers = new HashMap<>();
            totalMembers.put(WEEKDAY_CODE, weekdayMembers);
            totalMembers.put(DAY_OFF_CODE, dayOffMembers);
            return totalMembers;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getTotalMembers();
        }
    }

    private Crews getAndValidateDayOffMembers() {
        List<String> dayOffMembers = InputParser.parseInputByDelimiter(inputView.inputDayOffMembers(), DELIMITER_COMMA);
        return new Crews(dayOffMembers);
    }

    private Crews getAndValidateWeekdayMembers() {
        List<String> weekdayMembers = InputParser.parseInputByDelimiter(inputView.inputWeekdayMembers(), DELIMITER_COMMA);
        return new Crews(weekdayMembers);
    }
}
