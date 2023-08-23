package bc.bookchat.chat.domain.entity;

import bc.bookchat.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private Long roomId;
    private String sender;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
    }

    private Message(String content, Long roomId, String sender) {
        this.content = content;
        this.roomId = roomId;
        this.sender = sender;
    }

    public static Message create(String content, Long roomId, String sender) {
        return new Message(content, roomId, sender);
    }
}
