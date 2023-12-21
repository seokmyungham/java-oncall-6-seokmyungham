package oncall.domain;

import static oncall.constant.ErrorMessage.INVALID_INPUT_ERROR_MESSAGE;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Crews {
    private final static int MEMBER_MINIMUM_SIZE = 5;
    private final static int MEMBER_MAXIMUM_SIZE = 35;

    private final Deque<Name> crews;

    public Crews(List<String> memberNames) {
        validate(memberNames);
        this.crews = initializeCrews(memberNames);
    }

    public void validate(List<String> memberNames) {
        validateCrewsSize(memberNames);
        validateDuplicatedCrew(memberNames);
    }

    public Deque<Name> initializeCrews(List<String> memberNames) {
        Deque<Name> crews = new LinkedList<>();
        for (String name : memberNames) {
            crews.add(new Name(name));
        }
        return crews;
    }

    public void validateCrewsSize(List<String> memberNames) {
        if (memberNames.size() < MEMBER_MINIMUM_SIZE || memberNames.size() > MEMBER_MAXIMUM_SIZE) {
            throw new IllegalArgumentException(INVALID_INPUT_ERROR_MESSAGE);
        }
    }

    public void validateDuplicatedCrew(List<String> memberNames) {
        Set<String> duplicateMask = new HashSet<>(memberNames);

        if (memberNames.size() != duplicateMask.size()) {
            throw new IllegalArgumentException(INVALID_INPUT_ERROR_MESSAGE);
        }
    }

    public Deque<Name> getCrews() {
        return crews;
    }
}
