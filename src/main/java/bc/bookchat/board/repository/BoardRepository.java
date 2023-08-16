package bc.bookchat.board.repository;

import bc.bookchat.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
}
