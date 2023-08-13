package bc.bookchat.auth.controller.dto;

import bc.bookchat.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SignupResponse {

  private String email;

  private String userName;

  public static SignupResponse entityToDto(Member member) {
    return new SignupResponse(member.getEmail(),member.getUserName());
  }

}
