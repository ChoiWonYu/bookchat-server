package bc.bookchat.common.config;

import bc.bookchat.common.filter.JwtAuthorizationFilter;
import bc.bookchat.common.jwt.JwtProvider;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean jwtFilter(JwtProvider jwtProvider) {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
    filterRegistrationBean.setFilter(new JwtAuthorizationFilter(jwtProvider));
    filterRegistrationBean.setOrder(1);
    return filterRegistrationBean;
  }

}
