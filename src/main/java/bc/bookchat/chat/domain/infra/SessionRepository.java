package bc.bookchat.chat.domain.infra;

import bc.bookchat.chat.domain.entity.Session;
import bc.bookchat.room.domain.entity.Room;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findSessionsByRoom(Room room);

    void deleteByRoomAndUsername(Room room, String username);
}
