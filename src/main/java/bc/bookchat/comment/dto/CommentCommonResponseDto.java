package bc.bookchat.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class CommentCommonResponseDto {
    private Long commentId;

    private UUID boardId;

    private String content;

    private String commentWriter;
}
