package bc.bookchat.chat.presentation;

import bc.bookchat.auth.service.AuthService;
import bc.bookchat.chat.presentation.dto.MessageRequestDto;
import bc.bookchat.chat.service.ChatService;
import bc.bookchat.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;
    private final AuthService authService;
    // 채팅방 입장
    @MessageMapping("/chat/enter")
    public void enter(MessageRequestDto message, @Header("Authorization") String token) {
        log.info("user token: " + token);
        Member memberByJwt = authService.findMemberByJwt(token.substring(7));
        chatService.enter(message, memberByJwt);
    }

    // 메세지 보내기
    @MessageMapping("/chat/message")
    public void message(@Valid MessageRequestDto message, @Header("Authorization") String token) {
        log.info("user token: " + token);
        Member memberByJwt = authService.findMemberByJwt(token.substring(7));
        chatService.publish(message, memberByJwt);
    }

    // 채팅방 퇴장
    @MessageMapping("/chat/quit")
    public void quit(MessageRequestDto message, @Header("Authorization") String token) {
        log.info("user token: " + token);
        Member memberByJwt = authService.findMemberByJwt(token.substring(7));
        chatService.quit(message, memberByJwt);
    }
}