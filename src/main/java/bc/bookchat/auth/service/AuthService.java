package bc.bookchat.auth.service;

import bc.bookchat.auth.controller.dto.SignupRequestDto;
import bc.bookchat.auth.controller.dto.SignupResponse;
import bc.bookchat.member.entity.Member;
import bc.bookchat.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberRepository memberRepository;

  public SignupResponse register(SignupRequestDto signupRequestDto) {
    Member member=signupRequestDto.toEntity();
    memberRepository.save(member);

    return SignupResponse.entityToDto(member);
  }
}
