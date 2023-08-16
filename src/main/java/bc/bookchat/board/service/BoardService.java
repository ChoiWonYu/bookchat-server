package bc.bookchat.board.service;

import bc.bookchat.board.controller.dto.BoardPaginationQuery;
import bc.bookchat.board.controller.dto.CommonBoardResponse;
import bc.bookchat.board.entity.Board;
import bc.bookchat.board.repository.BoardRepository;
import bc.bookchat.book.repository.BookRepository;
import bc.bookchat.book.service.BookService;
import bc.bookchat.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BookRepository bookRepository;
    private final BookService bookService;
    private final BoardRepository boardRepository;

    @Transactional
    public PageResponse<CommonBoardResponse> getBookBoards(Long isbn, BoardPaginationQuery query) {
        if (bookRepository.findById(isbn).isEmpty()) {
            bookService.createBook(isbn);
        }

        Page<Board> boardsPage = getPaginationBoards(isbn, query);
        List<CommonBoardResponse> results = boardsPage.stream().map(Board::toDto).toList();
        return new PageResponse<>(results, query.toPageInfo(boardsPage));
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
