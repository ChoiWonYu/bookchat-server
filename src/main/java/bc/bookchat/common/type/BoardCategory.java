package bc.bookchat.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BoardCategory {
    QUESTION("QUESTION"),
    SOLUTION("SOLUTION"),
    TYPO("TYPO"),
    CONCEPT("CONCEPT");

    private final String value;

    @JsonCreator(mode=JsonCreator.Mode.DELEGATING)
    public static BoardCategory get(String code) {
        return Arrays.stream(values())
                .filter(type -> type.getValue().equals(code))
                .findAny()
                .orElse(null);
    }
}
