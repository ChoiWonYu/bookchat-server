package bc.bookchat.book.controller.dto;

import bc.bookchat.common.annotation.ValidEnum;
import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import bc.bookchat.common.type.SearchField;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Getter
@Setter
public class GetBookQuery {

  @NotBlank(message="query는 필수 입력값입니다.")
  private String query;

  @Min(1)
  private int page=1;

  @Min(1)
  private int size=3;

  @ValidEnum
  private SearchField searchField;

  public GetBookQuery(String query,SearchField searchField){
    this.query=query;
    this.searchField=searchField;
  }

  public URI toURI(String baseUrl){
    try {
      UriComponents uriBuilder = UriComponentsBuilder
              .fromHttpUrl(baseUrl)
              .queryParam("page", page)
              .queryParam("size", size)
              .queryParam("target", searchField.toString().toLowerCase())
              .queryParam("query", query)
              .encode()
              .build();

      return uriBuilder.toUri();
    }catch(Exception e){
      throw new CustomException(ErrorCode.INVALID_QUERY);
    }
  }

}
