package bc.bookchat.board.entity;

import bc.bookchat.board.controller.dto.BoardDetailResponse;
import bc.bookchat.board.controller.dto.BoardUpdateRequest;
import bc.bookchat.board.controller.dto.CommonBoardResponse;
import bc.bookchat.book.entity.MajorBook;
import bc.bookchat.comment.dto.CommentOwnerShipResponseDto;
import bc.bookchat.comment.entity.Comment;
import bc.bookchat.common.entity.BaseEntity;
import bc.bookchat.common.type.BoardCategory;
import bc.bookchat.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    UUID id;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private String imageUrl;

    private int views=0;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    private Long isbn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private MajorBook book;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void updateBoard(BoardUpdateRequest boardUpdateRequest) {
        this.title = boardUpdateRequest.getTitle();
        this.content = boardUpdateRequest.getContent();
        this.boardCategory=boardUpdateRequest.getBoardCategory();

        if (boardUpdateRequest.getImageUrl() != null) {
            this.imageUrl = boardUpdateRequest.getImageUrl();
        } else {
            this.imageUrl = null;
        }
    }

    public CommonBoardResponse toDto() {
        return CommonBoardResponse.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .createdAt(super.getCreatedAt())
                .imageUrl(imageUrl)
                .boardCategory(boardCategory)
                .commentCounts(comments.size())
                .views(views)
                .build();
    }

    public BoardDetailResponse toDetailDto(Member member){

        List<CommentOwnerShipResponseDto> commentsList = comments.stream()
                .map(comment -> comment.toOwnerShipDto(member)).toList();


        return BoardDetailResponse.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .isbn(isbn)
                .content(content)
                .createdAt(super.getCreatedAt())
                .isMine(member.getId().equals(writer.getId()))
                .comments(commentsList)
                .boardCategory(boardCategory)
                .views(views)
                .imageUrl(imageUrl)
                .build();
    }

    @Builder
    public Board(String title, String content, String imageUrl, Member member,MajorBook book,BoardCategory category) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.writer = member;
        this.book=book;
        this.isbn=book.getIsbn();
        this.boardCategory=category;
        this.views=0;
    }

    public void increaseViews(){
        views++;
    }
}
