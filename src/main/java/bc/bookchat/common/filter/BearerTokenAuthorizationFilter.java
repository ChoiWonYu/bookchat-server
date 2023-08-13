package bc.bookchat.common.filter;


import jakarta.servlet.Filter;

public interface BearerTokenAuthorizationFilter extends Filter {

  public boolean hasAuthorization(String authorizationHeader);

  public boolean isBearerToken(String token);
}
