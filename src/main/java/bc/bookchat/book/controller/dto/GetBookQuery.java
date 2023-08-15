package bc.bookchat.book.controller.dto;

import bc.bookchat.common.annotation.ValidEnum;
import bc.bookchat.common.type.SearchField;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
public class GetBookQuery {

  @NotBlank(message="query는 필수 입력값입니다.")
  private String query;

  private int page=1;
  private int size=3;

  @ValidEnum(enumClass = SearchField.class, message = "유효하지 않은 값입니다.")
  private SearchField searchField=SearchField.TITLE;

  public String toUrl(String baseUrl){
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
        .queryParam("page", page)
        .queryParam("size",size)
        .queryParam("target",searchField.toString().toLowerCase())
        .queryParam("query",query)
            .encode(StandardCharsets.UTF_8);

    return uriBuilder.toUriString();
  }

}
