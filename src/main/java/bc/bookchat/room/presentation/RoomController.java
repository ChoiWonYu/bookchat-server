package bc.bookchat.room.presentation;

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
    public ResponseEntity<RoomResponseDto> createRoom(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(roomService.createRoom(name));
    }

    // 모든 방 리스트 출력
    @GetMapping("/rooms")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(roomService.findAll());
    }

    // 채팅방 정보 출력 (1개)
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<RoomResponseDto> roomInfo(@PathVariable String roomId) {
        return ResponseEntity.ok(roomService.findRoom(roomId));
    }

}
