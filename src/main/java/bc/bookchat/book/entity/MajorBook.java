package bc.bookchat.book.entity;

import bc.bookchat.board.entity.Board;
import bc.bookchat.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MajorBook {
  @Id
  private Long isbn;

  private String title;

  private String author;

  private String imageUrl;

  @OneToMany(mappedBy = "book")
  private List<Board> boards;

  public MajorBook(Long isbn,String title,String author,String imageUrl){
    this.isbn=isbn;
    this.title=title;
    this.author=author;
    this.imageUrl=imageUrl;
  }
}
