package bc.bookchat.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

  @NotBlank(message="이름은 필수 입력값입니다.")
  private String email;

  @NotBlank(message="이름은 필수 입력값입니다.")
  private String password;
}
