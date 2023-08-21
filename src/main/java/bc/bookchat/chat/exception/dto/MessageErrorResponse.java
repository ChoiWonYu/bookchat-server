package bc.bookchat.chat.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class MessageErrorResponse {
    private String code;
    private String cause;
    @JsonInclude(Include.NON_EMPTY)
    private List<FieldError> validation;
    @Getter
    @Setter
    @NoArgsConstructor
    public static class FieldError {

        private String field;
        private String message;
    }

    public MessageErrorResponse(String code, String cause) {
        this.code = code;
        this.cause = cause;
    }
    public MessageErrorResponse(String code, String cause, List<FieldError> validation) {
        this.code = code;
        this.cause = cause;
        this.validation = validation;
    }
}
