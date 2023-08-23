package bc.bookchat.chat.domain.infra;

import bc.bookchat.chat.domain.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByRoomId(Long isbn);
}