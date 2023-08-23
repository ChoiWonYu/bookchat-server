package bc.bookchat.room.presentation;

import bc.bookchat.common.annotation.TokenInfo;
import bc.bookchat.common.response.ResponseHandler;
import bc.bookchat.member.entity.Member;
import bc.bookchat.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    // 방 생성
    @PostMapping("/rooms")
    public ResponseEntity<Object> createRoom(@RequestParam Long isbn) {
        return ResponseHandler.generateResponse("채팅방이 생성되었습니다.",
            HttpStatus.CREATED,
            roomService.createRoom(isbn));
    }

    // 접속한 적 있던 채팅방 목록 조회
    @GetMapping("/rooms/visited")
    public ResponseEntity<Object> findVisitedAll(@TokenInfo Member member) {
        return ResponseHandler.generateResponseWithoutMsg(HttpStatus.OK,
            roomService.findVisitedAll(member));
    }

    // 채팅방 이전 채팅 목록 조회
    @GetMapping("/rooms/{isbn}/messages")
    public ResponseEntity<Object> findRoomMessage(@PathVariable Long isbn) {
        return ResponseHandler.generateResponseWithoutMsg(HttpStatus.OK,
            roomService.findBeforeMessageAll(isbn));
    }
}
