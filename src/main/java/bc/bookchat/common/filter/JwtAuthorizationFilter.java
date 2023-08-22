package bc.bookchat.common.filter;

import bc.bookchat.auth.service.AuthService;
import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import bc.bookchat.common.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.cors.CorsUtils;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter implements BearerTokenAuthorizationFilter{

  private String[] whiteListURI=new String[] {"/auth/*", "/websocket-stomp/*", "/h2-console/*"};

  private final AuthService authService;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;

    if(checkWhiteList(httpServletRequest.getRequestURI())||CorsUtils.isPreFlightRequest(httpServletRequest)){
      chain.doFilter(request,response);
      return;
    }

    String authorization = httpServletRequest.getHeader("Authorization");

    if(!hasAuthorization(authorization)||!isBearerToken(authorization)){
      setErrorResponse(httpServletResponse,ErrorCode.UNAUTHENTICATED_USERS);
      return;
    }

    try{
      String token=getToken(httpServletRequest);
      authService.findMemberByJwt(token);
      chain.doFilter(request,response);
    }catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException |
            CustomException e) {
      setErrorResponse(httpServletResponse, ErrorCode.INVALID_TOKEN);
    } catch (ExpiredJwtException e) {
      setErrorResponse(httpServletResponse,ErrorCode.EXPIRED_TOKEN);
    }
  }

  @Override
  public boolean hasAuthorization(String authorizationHeader) {
    return authorizationHeader!=null;
  }

  @Override
  public boolean isBearerToken(String token) {
    return token.startsWith("Bearer ");

  }

  private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode){
    ObjectMapper objectMapper=new ObjectMapper();
    response.setStatus(errorCode.getStatus());
    response.setContentType("application/json; charset=UTF-8");

    ErrorResponse errorResponse=new ErrorResponse(errorCode);
    try{
      response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  private boolean checkWhiteList(String uri) {
    log.info(uri);
    return PatternMatchUtils.simpleMatch(whiteListURI, uri);
  }

  private String getToken(HttpServletRequest request){
    return request.getHeader("Authorization").substring(7);
  }
}
