package oncall.domain;

import static oncall.constant.ErrorMessage.NAME_LENGTH_ERROR_MESSAGE;

public class Name {
    private final String name;

    public Name(String name) {
        validateNameLength(name);
        this.name = name;
    }

    private void validateNameLength(String name) {
        if (name.length() > 5) {
            throw new IllegalArgumentException(NAME_LENGTH_ERROR_MESSAGE);
        }
    }
}
