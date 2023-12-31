package bc.bookchat.board.controller;

import bc.bookchat.board.controller.dto.*;
import bc.bookchat.board.entity.Board;
import bc.bookchat.board.service.BoardService;
import bc.bookchat.common.annotation.TokenInfo;
import bc.bookchat.common.response.PageResponse;
import bc.bookchat.common.response.ResponseHandler;
import bc.bookchat.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/books/{isbn}/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<Object> getBookBoards(@PathVariable Long isbn, @Valid BoardPaginationQuery query) {
        PageResponse<CommonBoardResponse> response = boardService.getBookBoards(isbn, query);
        return ResponseHandler.generateResponseWithoutMsg(HttpStatus.OK, response);
    }

    @GetMapping("/{category}")
    public ResponseEntity<Object> getBookBoardsByCategory(@Valid BoardCategoryPaginationQuery query) {
        PageResponse<CommonBoardResponse> response = boardService.getBookBoardsByCategory(query.getIsbn(), query.getCategory(), query);
        return ResponseHandler.generateResponseWithoutMsg(HttpStatus.OK, response);
    }

    @PostMapping
    public ResponseEntity<Object> createBookBoard(@Valid @RequestBody BoardCreateRequest boardCreateRequest, @PathVariable Long isbn
            , @TokenInfo Member member) {
        Board board = boardService.createBookBoard(isbn, boardCreateRequest, member);
        return ResponseHandler.generateResponse("게시물이 생성되었습니다.", HttpStatus.CREATED, board.toDto());
    }

    @GetMapping("/details/{boardId}")
    public ResponseEntity<Object> getBoardDetail(@PathVariable UUID boardId, @TokenInfo Member member) {
        Board board = boardService.viewBoardDetail(boardId);
        return ResponseHandler.generateResponseWithoutMsg(HttpStatus.OK, board.toDetailDto(member));
    }

    @PutMapping("/details/{boardId}")
    public ResponseEntity<Object> editBoard(@PathVariable UUID boardId, @TokenInfo Member member, @Valid @RequestBody BoardUpdateRequest boardUpdateRequest) {
        Board board = boardService.editBoard(boardId, member, boardUpdateRequest);
        return ResponseHandler.generateResponse("게시물이 수정되었습니다.", HttpStatus.CREATED, board.toDetailDto(member));
    }

    @DeleteMapping("/details/{boardId}")
    public ResponseEntity<Object> deleteBoard(@PathVariable UUID boardId, @TokenInfo Member member) {
        Board board = boardService.deleteBoard(boardId, member);
        return ResponseHandler.generateResponse("게시물이 삭제되었습니다.", HttpStatus.OK, board.toDetailDto(member));
    }

}
