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
    @NotBlank
    @Size(max = 20)
    private String message;
}
