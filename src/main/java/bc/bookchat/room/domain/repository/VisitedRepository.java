package bc.bookchat.room.domain.repository;

import bc.bookchat.room.domain.entity.Visited;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitedRepository extends JpaRepository<Visited, Long> {
    List<Visited> findAllByMember_Email(String email);

    List<Visited> findDistinctRoom_IdByMember_UserNameOrderByEnterAtDesc(String username);

    List<Visited> findDistinctMember_IdByRoom_RoomIdOrderByEnterAtDesc(Long roomId);
}
