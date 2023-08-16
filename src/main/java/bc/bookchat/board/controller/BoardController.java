package bc.bookchat.board.controller;

import bc.bookchat.board.controller.dto.BoardPaginationQuery;
import bc.bookchat.board.controller.dto.CommonBoardResponse;
import bc.bookchat.board.service.BoardService;
import bc.bookchat.common.response.PageResponse;
import bc.bookchat.common.response.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{isbn}")
    public ResponseEntity<Object> getBookBoards(@PathVariable Long isbn, BoardPaginationQuery query){
        PageResponse<CommonBoardResponse>response=boardService.getBookBoards(isbn,query);
        return ResponseHandler.generateResponseWithoutMsg( HttpStatus.OK,response);
    }
}
