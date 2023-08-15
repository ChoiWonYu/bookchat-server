package bc.bookchat.book.controller;

import bc.bookchat.book.controller.dto.BookInfo;
import bc.bookchat.book.controller.dto.GetBookQuery;
import bc.bookchat.book.service.BookService;
import bc.bookchat.common.response.PageResponse;
import bc.bookchat.common.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping()
  public ResponseEntity<Object> getBooks(GetBookQuery query){

    PageResponse<BookInfo> response=bookService.getBooksPageResponse(query);
    return ResponseHandler.generateResponseWithoutMsg(HttpStatus.OK,response);
  }


}
