package bc.bookchat.room.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String roomId;

    @Column(name = "create_at")
    private LocalDate createAt;
    @PrePersist
    protected void onCreate() {
        createAt = LocalDate.now();
    }

    @OneToMany(mappedBy = "room")
    private List<Session> sessionList;

    @OneToMany(mappedBy = "room")
    private List<Visited> visitedList;

    private Room(String name, String roomId) {
        this.name = name;
        this.roomId = roomId;
    }

    public static Room create(String name) {
        return new Room(name, UUID.randomUUID().toString());
    }
}
