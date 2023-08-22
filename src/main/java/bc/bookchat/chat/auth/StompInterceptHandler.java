package bc.bookchat.chat.auth;

import bc.bookchat.auth.service.AuthService;
import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import bc.bookchat.common.jwt.JwtProvider;
import bc.bookchat.member.entity.Member;
import bc.bookchat.room.domain.entity.Room;
import bc.bookchat.room.domain.entity.Session;
import bc.bookchat.room.domain.repository.RoomRepository;
import bc.bookchat.room.domain.repository.SessionRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompInterceptHandler implements ChannelInterceptor {
    private final JwtProvider jwtProvider;

    /**
     * CONNECT: JWT 토큰 체크 및 유저정보 받아오기
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);


        if (StompCommand.CONNECT == accessor.getCommand()) {
            String jwt = accessor.getFirstNativeHeader("Authorization");

            // CHECK has Authorization, is BearerToken
            if (!hasAuthorization(jwt) || !isBearerToken(jwt)) {
                throw new CustomException(ErrorCode.UNAUTHENTICATED_USERS);
            }

            // CHECK Invalid Token
            try{
                String token = jwt.substring(7); // "Bearer " 부분 제거
                jwtProvider.parseClaims(token);
            } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                throw new CustomException(ErrorCode.INVALID_TOKEN);
            } catch (ExpiredJwtException e) {
                throw new CustomException(ErrorCode.EXPIRED_TOKEN);
            }

        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
        }

        return message;
    }

    public boolean hasAuthorization(String authorizationHeader) {
        return authorizationHeader!=null;
    }

    public boolean isBearerToken(String token) {
        return token.startsWith("Bearer ");
    }
}
