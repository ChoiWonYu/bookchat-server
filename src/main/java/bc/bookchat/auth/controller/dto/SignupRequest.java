package bc.bookchat.auth.controller.dto;

import bc.bookchat.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {

  @NotBlank(message="이메일은 필수 입력값입니다.")
  @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",message = "이메일 형식에 맞지 않습니다.")
  private String email;

  @NotBlank(message="이름은 필수 입력값입니다.")
  private String userName;

  @NotBlank(message="비밀번호는 필수 입력값입니다.")
  private String password;

  public Member toEntity(){
    return Member.builder()
        .email(email)
        .userName(userName)
        .password(password)
        .build();
  }

}
