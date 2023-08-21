package bc.bookchat.chat.auth;

import bc.bookchat.chat.auth.dto.StompPrincipal;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Component
public class StompHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(
        ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        String sessionId = UUID.randomUUID().toString();
        logger.info(sessionId);

        return new StompPrincipal(sessionId);
    }
}
