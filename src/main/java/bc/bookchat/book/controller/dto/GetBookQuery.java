package bc.bookchat.book.controller.dto;

import bc.bookchat.common.type.SearchField;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
public class GetBookQuery {

  private String query;
  private int page=1;
  private int size=3;
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
