package bc.bookchat.common.config;

import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import bc.bookchat.common.exception.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 요청 dto가 유효성 검사에서 틀렸을 때 발생
   * */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    List<ErrorResponse.FieldError> errors = new ArrayList<>();
    for (FieldError fieldError : e.getFieldErrors()) {
      log.error("name:{}, message:{}", fieldError.getField(), fieldError.getDefaultMessage());
      ErrorResponse.FieldError error = new ErrorResponse.FieldError();
      error.setField(fieldError.getField());
      error.setMessage(fieldError.getDefaultMessage());

      errors.add(error);
    }

    ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST, errors);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * 요청 param가 전해지지 않을 때 발생
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException e) {
    ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * JPA를 통해 DB 조작시 발생 ConstraintViolationException : 제약 조건 위배되었을 때 발생
   * DataIntegrityViolationException : 데이터의 삽입/수정이 무결성 제약 조건을 위반할 때 발생
   */
  @ExceptionHandler(value = {ConstraintViolationException.class,
      DataIntegrityViolationException.class})
  protected ResponseEntity<ErrorResponse> handleDataException(Exception e) {
    ErrorResponse response = new ErrorResponse(ErrorCode.DUPLICATE_RESOURCE);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * enum type 일치하지 않아 binding 못할 경우 발생 주로 @RequestParam enum으로 binding 못했을 경우 발생
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * 지원하지 않은 HTTP method 호출 할 경우 발생
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    ErrorResponse response = new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생
   */
  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
    ErrorResponse response = new ErrorResponse(ErrorCode.ACCESS_DENIED);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * 만료된 토큰으로의 요청일 때 발생
   * */
  @ExceptionHandler(ExpiredJwtException.class)
  protected ResponseEntity<ErrorResponse> handleExpiredTokenException(ExpiredJwtException e) {
    ErrorResponse response = new ErrorResponse(ErrorCode.EXPIRED_TOKEN);
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  @ExceptionHandler(value = {SignatureException.class, MalformedJwtException.class,
      UnsupportedJwtException.class, IllegalArgumentException.class})
  protected ResponseEntity<ErrorResponse> handleWrongTokenException(Exception e) {
    ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_TOKEN);
    return ResponseEntity.status(response.getStatus()).body(response);
  }


  /**
   * Business Logic 수행 중 발생시킬 커스텀 에러
   */
  @ExceptionHandler(value = {CustomException.class})
  protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
    ErrorResponse response = new ErrorResponse(
        e.getErrorCode()); // CustomException에 ErrorCode Enum 반환
    return ResponseEntity.status(response.getStatus()).body(response);
  }

  /**
   * 위에 해당하는 예외에 해당하지 않을 때 모든 예외를 처리하는 메소드
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    e.printStackTrace();
    ErrorResponse response = new ErrorResponse(ErrorCode.SERVER_ERROR);
    return ResponseEntity.status(response.getStatus()).body(response);
  }


}
