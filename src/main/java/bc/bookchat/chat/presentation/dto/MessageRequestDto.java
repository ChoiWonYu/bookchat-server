package bc.bookchat.chat.presentation.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequestDto {
    private String roomId;
    private String sender;
    @NotBlank
    @Size(max = 20)
    private String message;

    private MessageRequestDto(String roomId, String sender, String message) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
    }

    public static MessageRequestDto toDto(String roomId, String sender, String message) {
        return new MessageRequestDto(roomId, sender, message);
    }
}
