package bc.bookchat.room.dto;

import lombok.Getter;

@Getter
public class RoomResponseDto {
    private Long roomId;
    private String name;

    private RoomResponseDto(Long roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public static RoomResponseDto toDto(Long roomId, String name) {
        return new RoomResponseDto(roomId, name);
    }
}
