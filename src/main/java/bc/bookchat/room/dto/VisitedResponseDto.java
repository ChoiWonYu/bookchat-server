package bc.bookchat.room.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VisitedResponseDto {
    private Long isbn;
    private String roomName;
    private String enterAt;
    private String imageUrl;
    private String writer;
    public static VisitedResponseDto toDto(
        Long isbn,
        String roomName,
        String enterAt,
        String imageUrl,
        String writer) {
        return new VisitedResponseDto(isbn, roomName, enterAt, imageUrl, writer);
    }
}
