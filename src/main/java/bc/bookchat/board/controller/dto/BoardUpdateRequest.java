package bc.bookchat.board.controller.dto;

import bc.bookchat.common.annotation.ValidEnum;
import bc.bookchat.common.type.BoardCategory;
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

    @ValidEnum
    private BoardCategory boardCategory;

    private String imageUrl;
}
