package bc.bookchat.common.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SearchField {
    TITLE("TITLE"),
    AUTHOR("AUTHOR"),
    ISBN("ISBN");

    private final String value;

    @JsonCreator(mode=JsonCreator.Mode.DELEGATING)
    public static SearchField get(String code) {
        return Arrays.stream(values())
                .filter(type -> type.getValue().equals(code))
                .findAny()
                .orElse(null);
    }
}
