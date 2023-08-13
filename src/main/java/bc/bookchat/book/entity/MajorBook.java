package bc.bookchat.book.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MajorBook {
  @Id
  Long isbn;

  String title;

  String author;

  String imageUrl;
}
