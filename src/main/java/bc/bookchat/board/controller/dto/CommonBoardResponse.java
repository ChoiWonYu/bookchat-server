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
public class CommonBoardResponse {
    private UUID id;

    private String title;

    private String content;

    private String writer;

    private String imageUrl;

    private LocalDateTime createdAt;

    private BoardCategory boardCategory;

    private int views;

    private int commentCounts;

    @Builder
    public CommonBoardResponse(UUID id, String title, String content, Member writer, LocalDateTime createdAt, String imageUrl,BoardCategory boardCategory,int commentCounts,int views){
        this.id=id;
        this.title=title;
        this.writer=writer.getUserName();
        this.content=content;
        this.imageUrl=imageUrl;
        this.createdAt=createdAt;
        this.boardCategory=boardCategory;
        this.commentCounts=commentCounts;
        this.views=views;
    }
}
