package bc.bookchat.book.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MajorBook {
  @Id
  Long isbn;

  String title;

  String author;

  String imageUrl;

}
