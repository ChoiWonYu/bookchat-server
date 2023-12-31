package bc.bookchat.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentUpdateRequest {
    @NotBlank(message = "내용은 필수 입력값입니다.")
    String content;
}
