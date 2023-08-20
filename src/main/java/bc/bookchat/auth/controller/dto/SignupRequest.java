package bc.bookchat.auth.controller.dto;

import bc.bookchat.member.entity.Member;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(min = 2, max = 10, message = "이름은 2자 이상, 10자 이하여야 합니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 4, max = 16, message = "비밀번호는 4자 이상, 16자 이하여야 합니다.")
    private String password;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .userName(userName)
                .password(password)
                .build();
    }

}
