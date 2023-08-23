package bc.bookchat.chat.presentation.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageBeforeResponseDto {
    private String content;
    private Long roomId;
    private String sender;
    private String createAt;

    public static MessageBeforeResponseDto toDto(String content, Long roomId, String sender, String createAt) {
        return new MessageBeforeResponseDto(content, roomId, sender, createAt);
    }
}
