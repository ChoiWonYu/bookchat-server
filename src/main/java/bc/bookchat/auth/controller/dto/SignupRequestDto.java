package bc.bookchat.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;

public class SignupRequestDto {

  @NotBlank(message="이메일은 필수 입력값입니다.")
  private String email;

  @NotBlank(message="이름은 필수 입력값입니다.")
  private String userName;

  @NotBlank(message="비밀번호는 필수 입력값입니다.")
  private String password;

}
