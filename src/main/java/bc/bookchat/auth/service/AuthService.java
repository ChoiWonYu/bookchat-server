package bc.bookchat.auth.service;

import bc.bookchat.auth.controller.dto.LoginRequest;
import bc.bookchat.auth.controller.dto.SignupRequest;
import bc.bookchat.auth.controller.dto.SignupResponse;
import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import bc.bookchat.common.jwt.JwtProvider;
import bc.bookchat.member.entity.Member;
import bc.bookchat.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

  private final MemberRepository memberRepository;

  private final JwtProvider jwtProvider;

  @Transactional
  public SignupResponse register(SignupRequest signupRequest) {
    Member member=signupRequest.toEntity();
    memberRepository.save(member);

    return SignupResponse.entityToDto(member);
  }

  public String login(LoginRequest loginRequest) {
    Member member=memberRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));

    String accessToken=jwtProvider.createAccessToken(member.getEmail());

    return accessToken;
  }

  public Member findMemberByJwt(String token) {
    String memberFormId = jwtProvider.getPayload(token);

    return memberRepository.findByEmail(memberFormId)
        .orElseThrow(()->new CustomException(ErrorCode.MEMBER_NOT_FOUND));
  }

}
