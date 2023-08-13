package bc.bookchat.auth.controller;

import bc.bookchat.auth.controller.dto.LoginRequest;
import bc.bookchat.auth.controller.dto.LoginResponse;
import bc.bookchat.auth.controller.dto.SignupRequest;
import bc.bookchat.auth.controller.dto.SignupResponse;
import bc.bookchat.auth.service.AuthService;
import bc.bookchat.common.response.ResponseHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<Object> register(@Valid @RequestBody SignupRequest signupRequest) {
    SignupResponse result = authService.register(signupRequest);
    return ResponseHandler.generateResponse("회원가입에 성공했습니다.",HttpStatus.CREATED, result);
  }

  @PostMapping("/signin")
  public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
    String accessToken=authService.login(loginRequest);
    return ResponseHandler.generateResponse("로그인에 성공했습니다.",HttpStatus.CREATED,new LoginResponse(accessToken));
  }}
