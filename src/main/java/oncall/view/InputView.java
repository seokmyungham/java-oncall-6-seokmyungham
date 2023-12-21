package oncall.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public static final String MONTH_AND_DAY_OF_WEEK_INPUT_PROMPT = "비상 근무를 배정할 월과 시작 요일을 입력하세요> ";
    public static final String WEEKDAY_MEMBERS_INPUT_PROMPT = "평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ";
    public static final String DAY_OFF_MEMBERS_INPUT_PROMPT = "휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ";

    public String inputMonthAndDayOfWeek() {
        System.out.printf(MONTH_AND_DAY_OF_WEEK_INPUT_PROMPT);
        return Console.readLine();
    }

    public String inputWeekdayMembers() {
        System.out.printf(WEEKDAY_MEMBERS_INPUT_PROMPT);
        return Console.readLine();
    }

    public String inputDayOffMembers() {
        System.out.printf(DAY_OFF_MEMBERS_INPUT_PROMPT);
        return Console.readLine();
    }
}
