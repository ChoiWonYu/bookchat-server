package bc.bookchat.member.entity;

import bc.bookchat.board.entity.Board;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(unique = true)
  private String email;

  private String password;

  @Column(unique = true)
  private String userName;

  @OneToMany(mappedBy = "writer")
  private List<Board> boards;

  @Builder
  public Member(String email, String password, String userName) {
    this.email=email;
    this.password=password;
    this.userName=userName;
  }
}
