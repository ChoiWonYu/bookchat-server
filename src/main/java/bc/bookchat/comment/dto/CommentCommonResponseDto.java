package bc.bookchat.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CommentCommonResponseDto {
    private Long commentId;

    private UUID boardId;

    private String content;

    private String commentWriter;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
