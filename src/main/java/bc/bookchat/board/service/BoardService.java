package bc.bookchat.board.service;

import bc.bookchat.board.controller.dto.BoardCreateRequest;
import bc.bookchat.board.controller.dto.BoardPaginationQuery;
import bc.bookchat.board.controller.dto.CommonBoardResponse;
import bc.bookchat.board.entity.Board;
import bc.bookchat.board.repository.BoardRepository;
import bc.bookchat.book.entity.MajorBook;
import bc.bookchat.book.repository.BookRepository;
import bc.bookchat.book.service.BookService;
import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import bc.bookchat.common.response.PageResponse;
import bc.bookchat.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BookService bookService;
    private final BoardRepository boardRepository;

    @Transactional
    public PageResponse<CommonBoardResponse> getBookBoards(Long isbn, BoardPaginationQuery query) {

        bookService.findMajorBookByIsbn(isbn);

        Page<Board> boardsPage = getPaginationBoards(isbn, query);
        List<CommonBoardResponse> results = boardsPage.stream().map(Board::toDto).toList();
        return new PageResponse<>(results, query.toPageInfo(boardsPage));
    }

    public Board createBookBoard(Long isbn, BoardCreateRequest boardCreateRequest, Member member) {
        MajorBook book=bookService.findMajorBookByIsbn(isbn);
        return boardRepository.save(boardCreateRequest.toEntity(member,book));
    }

    public Board getBoardDetail(UUID boardId) {
        return boardRepository.findById(boardId).orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }


    private Page<Board> getPaginationBoards(Long isbn, BoardPaginationQuery query) {
        PageRequest pageRequest = makePageRequest(query);

        if (query.hasKeyword()) {
            return boardRepository.findByIsbnAndTitleContaining(isbn,
                    query.getKeyword(), pageRequest);
        }
        return this.boardRepository.findByIsbn(isbn, pageRequest);
    }

    private PageRequest makePageRequest(BoardPaginationQuery boardPaginationDto) {
        return PageRequest.of(boardPaginationDto.getPage() - 1,
                boardPaginationDto.getSize());
    }
}
