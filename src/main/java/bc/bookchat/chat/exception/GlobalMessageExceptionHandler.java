package bc.bookchat.chat.exception;

import bc.bookchat.chat.exception.dto.MessageErrorResponse;
import bc.bookchat.chat.exception.dto.MessageErrorResponse.FieldError;
import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalMessageExceptionHandler {

    private final SimpMessageSendingOperations messageTemplate;

    /**
     * CustomException
     * Business Logic Exception
     * */
    @MessageExceptionHandler(CustomException.class)
    public void CustomExceptionHandler(CustomException e, Principal principal)
        throws JsonProcessingException {
        //JSON으로 설정
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setContentType(MimeTypeUtils.APPLICATION_JSON);

        // 응답 메세지 커스텀
        MessageErrorResponse messageErrorResponse = new MessageErrorResponse(
            e.getErrorCode().getCode(), e.getErrorCode().getCause());

        // JSON 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String writtenValueAsString = objectMapper.writeValueAsString(messageErrorResponse);
        log.error("error response: " + writtenValueAsString);

        // 전송
        messageTemplate.convertAndSendToUser(principal.getName(), "/queue/error", writtenValueAsString);
    }

    /**
     * MethodArgumentNotValidException
     * 요청 dto가 유효성 검사에서 틀렸을 때 발생
     * */
    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e, Principal principal) throws JsonProcessingException {
        List<FieldError> errors = new ArrayList<>();

        for (org.springframework.validation.FieldError fieldError : e.getBindingResult()
            .getFieldErrors()) {
            log.error("name:{}, message:{}", fieldError.getField(), fieldError.getDefaultMessage());

            MessageErrorResponse.FieldError error = new MessageErrorResponse.FieldError();
            error.setField(fieldError.getField());
            error.setMessage(fieldError.getDefaultMessage());

            errors.add(error);
        }
        MessageErrorResponse response = new MessageErrorResponse(
            ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getCause(), errors);

        ObjectMapper objectMapper = new ObjectMapper();
        String writtenValueAsString = objectMapper.writeValueAsString(response);
        log.error("error response: " + writtenValueAsString);

        messageTemplate.convertAndSendToUser(principal.getName(), "/queue/error", writtenValueAsString);
    }
}
