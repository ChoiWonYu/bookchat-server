package bc.bookchat.room.presentation;

import bc.bookchat.common.response.ResponseHandler;
import bc.bookchat.room.dto.RoomResponseDto;
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
    public ResponseEntity<Object> createRoom(@RequestParam String name) {
        return ResponseHandler.generateResponse("채팅방이 생성되었습니다.",
            HttpStatus.CREATED,
            roomService.createRoom(name));
    }

    // 모든 방 리스트 출력
    @GetMapping("/rooms")
    public ResponseEntity<Object> findAll() {
        return ResponseHandler.generateResponseWithoutMsg(HttpStatus.OK,
            roomService.findAll());
    }

    // 채팅방 정보 출력 (1개)
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<Object> roomInfo(@PathVariable String roomId) {
        return ResponseHandler.generateResponseWithoutMsg(HttpStatus.OK,
            roomService.findRoom(roomId));
    }

}
