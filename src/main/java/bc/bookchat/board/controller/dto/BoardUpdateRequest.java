package bc.bookchat.board.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateRequest {
    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String content;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String title;

    private String imageUrl;
}
