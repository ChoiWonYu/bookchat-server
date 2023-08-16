package bc.bookchat.board.repository;

import bc.bookchat.board.entity.Board;
import bc.bookchat.common.type.BoardCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
    Page<Board> findByIsbnAndTitleContaining(Long isbn,String keyword, Pageable pageRequest);
    Page<Board> findByIsbn(Long isbn,Pageable pageRequest);

    Page<Board> findByIsbnAndBoardCategory(Long isbn, BoardCategory boardCategory,Pageable pageRequest);

    Page<Board> findByIsbnAndBoardCategoryAndTitleContaining(Long isbn,BoardCategory boardCategory,String keyword, Pageable pageRequest);
}
