package bc.bookchat.comment.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreateRequest {
    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;
}
