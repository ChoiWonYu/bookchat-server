package bc.bookchat.chat.domain.infra;

import bc.bookchat.chat.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
