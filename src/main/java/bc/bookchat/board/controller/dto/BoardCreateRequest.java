package bc.bookchat.board.controller.dto;

import bc.bookchat.board.entity.Board;
import bc.bookchat.book.entity.MajorBook;
import bc.bookchat.common.annotation.ValidEnum;
import bc.bookchat.common.type.BoardCategory;
import bc.bookchat.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BoardCreateRequest {
    @NotBlank(message="제목은 필수 입력값입니다.")
    private String title;

    @NotBlank(message="내용은 필수 입력값입니다.")
    private String content;

    private String imageUrl;

    @ValidEnum
    private BoardCategory boardCategory;

    public Board toEntity(Member member, MajorBook book){
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .member(member)
                .imageUrl(imageUrl)
                .book(book)
                .category(boardCategory)
                .build();
    }
}
