package oncall.util;

import java.util.ArrayList;
import java.util.List;

public class InputParser {
    public static List<String> parseInputByDelimiter(String input, String DELIMITER) {
        return new ArrayList<>(List.of(input.split(DELIMITER)));
    }
}
