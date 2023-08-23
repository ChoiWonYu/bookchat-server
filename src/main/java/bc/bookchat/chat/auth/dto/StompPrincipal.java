package bc.bookchat.chat.auth.dto;

import java.security.Principal;
import lombok.Getter;
import lombok.Setter;

@Getter
public class StompPrincipal implements Principal {

    private final String sessionId;

    public StompPrincipal(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getName() {
        return this.sessionId;
    }
}
