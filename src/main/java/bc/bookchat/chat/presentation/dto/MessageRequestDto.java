package bc.bookchat.chat.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequestDto {
    private Long roomId;
    @NotBlank
    private String sessionId;
    @NotBlank
    private String message;
}
