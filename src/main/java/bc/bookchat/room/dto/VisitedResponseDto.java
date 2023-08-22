package bc.bookchat.room.dto;

public class VisitedResponseDto {
    private String roomName;
    private String enterAt;

    private VisitedResponseDto(String roomName, String enterAt) {
        this.roomName = roomName;
        this.enterAt = enterAt;
    }
    public static VisitedResponseDto create(String roomName, String enterAt) {
        return new VisitedResponseDto(roomName, enterAt);
    }
}
