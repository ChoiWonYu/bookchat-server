package bc.bookchat.chat.exception;

import bc.bookchat.chat.exception.dto.MessageErrorResponse;
import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

@Component
@Slf4j
public class StompErrorHandler extends StompSubProtocolErrorHandler {

    public StompErrorHandler() {
        super();
    }

    @Override
    public Message<byte[]> handleClientMessageProcessingError(@Nullable Message<byte[]> clientMessage, Throwable ex) {

        log.debug(ex.getCause().getMessage()); // Throwable.getCause(): 근본적인 예외 반환
        Throwable error = ex.getCause();

        if (error instanceof CustomException) {
            ErrorCode errorCode = ((CustomException) error).getErrorCode();
            return prepareErrorMessage(errorCode);
        }

        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> prepareErrorMessage(ErrorCode errorCode) {
        // SET response Dto
        MessageErrorResponse messageErrorResponse = new MessageErrorResponse(errorCode.getCode(),
            errorCode.getCause());

        // SET Header
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setLeaveMutable(true);
        accessor.setContentType(MimeTypeUtils.APPLICATION_JSON);

        // BINDING toJSONString
        ObjectMapper objectMapper = new ObjectMapper();
        String messageErrorResponseJSON = null;
        try {
            messageErrorResponseJSON = objectMapper.writeValueAsString(messageErrorResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return MessageBuilder.createMessage(messageErrorResponseJSON.getBytes(StandardCharsets.UTF_8),
            accessor.getMessageHeaders());
    }

}
