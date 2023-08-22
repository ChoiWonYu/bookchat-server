package bc.bookchat.chat.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @Column(name = "create_at")
    private LocalDate createAt;

    @PrePersist
    protected void onCreate() {
        createAt = LocalDate.now();
    }

    private Message(String content) {
        this.content = content;
    }

    public static Message create(String content) {
        return new Message(content);
    }
}
