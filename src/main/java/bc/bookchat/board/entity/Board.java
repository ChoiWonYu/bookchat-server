package bc.bookchat.board.entity;

import bc.bookchat.book.entity.MajorBook;
import bc.bookchat.common.entity.BaseEntity;
import bc.bookchat.common.type.BoardCategory;
import bc.bookchat.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

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

    private String content;

    private String imageUrl;

    private int views;

    private BoardCategory boardCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private MajorBook book;

}
