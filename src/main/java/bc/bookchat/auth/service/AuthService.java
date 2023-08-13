package bc.bookchat.auth.service;

import bc.bookchat.auth.controller.dto.SignupRequest;
import bc.bookchat.auth.controller.dto.SignupResponse;
import bc.bookchat.member.entity.Member;
import bc.bookchat.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberRepository memberRepository;

  public SignupResponse register(SignupRequest signupRequest) {
    Member member=signupRequest.toEntity();
    memberRepository.save(member);

    return SignupResponse.entityToDto(member);
  }
}
