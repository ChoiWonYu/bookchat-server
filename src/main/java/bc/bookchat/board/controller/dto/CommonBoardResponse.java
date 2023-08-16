package bc.bookchat.board.controller.dto;

import bc.bookchat.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonBoardResponse {
    private UUID id;

    private String title;

    private String content;

    private String writer;

    private String imageUrl;

    private LocalDateTime createdAt;

    @Builder
    public CommonBoardResponse(UUID id, String title, String content, Member writer, LocalDateTime createdAt, String imageUrl){
        this.id=id;
        this.title=title;
        this.writer=writer.getUserName();
        this.content=content;
        this.imageUrl=imageUrl;
        this.createdAt=createdAt;
    }
}
