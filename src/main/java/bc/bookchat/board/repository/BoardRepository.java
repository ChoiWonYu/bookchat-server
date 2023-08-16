package bc.bookchat.board.repository;

import bc.bookchat.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
    Page<Board> findByIsbnAndTitleContaining(Long isbn,String keyword, Pageable pageRequest);
    Page<Board> findByIsbn(Long isbn,Pageable pageRequest);
}
