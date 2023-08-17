package bc.bookchat.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CommentOwnerShipResponseDto {
    private Long commentId;

    private UUID boardId;

    private String content;

    private String commentWriter;

    private boolean isMine;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
