package bc.bookchat.board.controller.dto;

import bc.bookchat.common.type.BoardCategory;
import bc.bookchat.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardDetailResponse {
    private UUID id;

    private Long isbn;

    private String title;

    private String content;

    private String writer;

    private BoardCategory boardCategory;

    private LocalDateTime createdAt;

    private boolean isMine;

    @Builder
    public BoardDetailResponse(UUID id, String title,Long isbn, String content, Member writer, LocalDateTime createdAt, boolean isMine,BoardCategory boardCategory){
        this.id=id;
        this.isbn=isbn;
        this.title=title;
        this.writer=writer.getUserName();
        this.content=content;
        this.createdAt=createdAt;
        this.isMine=isMine;
        this.boardCategory=boardCategory;
    }
}