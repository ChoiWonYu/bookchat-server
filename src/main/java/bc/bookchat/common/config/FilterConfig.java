package bc.bookchat.common.config;

import bc.bookchat.auth.service.AuthService;
import bc.bookchat.common.filter.JwtAuthorizationFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean jwtFilter(AuthService authService) {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new JwtAuthorizationFilter(authService));
    filterRegistrationBean.setOrder(1);
    return filterRegistrationBean;
  }

}
