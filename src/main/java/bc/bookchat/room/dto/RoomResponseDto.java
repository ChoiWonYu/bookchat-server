package bc.bookchat.room.dto;

import lombok.Getter;

@Getter
public class RoomResponseDto {
    private String roomId;
    private String name;

    private RoomResponseDto(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public static RoomResponseDto toDto(String roomId, String name) {
        return new RoomResponseDto(roomId, name);
    }
}
