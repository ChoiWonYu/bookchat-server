package bc.bookchat.comment.entity;

import bc.bookchat.board.entity.Board;
import bc.bookchat.comment.dto.CommentCommonResponseDto;
import bc.bookchat.comment.dto.CommentOwnerShipResponseDto;
import bc.bookchat.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id",nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member commentWriter;

    public Comment(String content,Member member,Board board){
        this.content=content;
        this.commentWriter=member;
        this.board=board;
    }

    public CommentCommonResponseDto toDto() {
        return new CommentCommonResponseDto(board.getId(),content,commentWriter.getUserName());
    }

    public CommentOwnerShipResponseDto toOwnerShipDto(Member member) {
        return new CommentOwnerShipResponseDto(board.getId(),content,commentWriter.getUserName(),commentWriter.equals(member));
    }

    public void addToBoard(Board board){
        board.getComments().add(this);
    }
}
