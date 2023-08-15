package bc.bookchat.book.repository;

import bc.bookchat.book.entity.MajorBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<MajorBook,Long> {

}
