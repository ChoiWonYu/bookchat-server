package bc.bookchat.board.service;

import bc.bookchat.board.controller.dto.*;
import bc.bookchat.board.entity.Board;
import bc.bookchat.board.repository.BoardRepository;
import bc.bookchat.book.entity.MajorBook;
import bc.bookchat.book.service.BookService;
import bc.bookchat.common.exception.CustomException;
import bc.bookchat.common.exception.ErrorCode;
import bc.bookchat.common.response.PageResponse;
import bc.bookchat.common.type.BoardCategory;
import bc.bookchat.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BookService bookService;
    private final BoardRepository boardRepository;

    @Transactional
    public PageResponse<CommonBoardResponse> getBookBoards(Long isbn, BoardPaginationQuery query) {

        findBookOrCreate(isbn);

        Page<Board> boardsPage = getPaginationBoards(isbn, query);
        List<CommonBoardResponse> results = boardsPage.stream().map(Board::toDto).toList();
        return new PageResponse<>(results, query.toPageInfo(boardsPage));
    }

    @Transactional
    public Board createBookBoard(Long isbn, BoardCreateRequest boardCreateRequest, Member member) {
        Optional<MajorBook> book=bookService.findMajorBookByIsbn(isbn);
        if(book.isEmpty()){
            throw new CustomException(ErrorCode.ISBN_NOT_FOUND);
        }

        return boardRepository.save(boardCreateRequest.toEntity(member,book.get()));
    }

    @Transactional
    public Board viewBoardDetail(UUID boardId) {
        Board board=getBoardDetail(boardId);
        board.increaseViews();
        return board;
    }

    @Transactional
    public Board getBoardDetail(UUID boardId) {
         return boardRepository.findById(boardId).orElseThrow(()->new CustomException(ErrorCode.BOARD_NOT_FOUND));
    }

    @Transactional
    public Board editBoard(UUID boardId, Member member, BoardUpdateRequest boardUpdateRequest) {
         Board board=getBoardDetail(boardId);
         validateMember(member,board);
         board.updateBoard(boardUpdateRequest);
         return board;
    }

    @Transactional
    public Board deleteBoard(UUID boardId, Member member) {
        Board board=getBoardDetail(boardId);
        validateMember(member,board);
        boardRepository.delete(board);
        return board;
    }

    @Transactional
    public PageResponse<CommonBoardResponse> getBookBoardsByCategory(Long isbn, BoardCategory category, BoardPaginationQuery query) {

        findBookOrCreate(isbn);

        Page<Board> boardsPage = getPaginationCategoryBoards(isbn, query,category);
        List<CommonBoardResponse> results = boardsPage.stream().map(Board::toDto).toList();
        return new PageResponse<>(results, query.toPageInfo(boardsPage));

    }

    @Transactional
    public void findBookOrCreate(Long isbn){
        Optional<MajorBook> book=bookService.findMajorBookByIsbn(isbn);
        if(book.isEmpty()){
            bookService.createBook(isbn);
        }
    }

    private void validateMember(Member member,Board board){
        if(!isMyBoard(member,board)){
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    private boolean isMyBoard(Member member,Board board){
        return member.getId().equals(board.getWriter().getId());
    }

    @Transactional(readOnly = true)
    public Page<Board> getPaginationCategoryBoards(Long isbn, BoardPaginationQuery query,BoardCategory category) {
        PageRequest pageRequest = makePageRequest(query);

        if (query.hasKeyword()) {
            return boardRepository.findByIsbnAndBoardCategoryAndTitleContaining(isbn,
                    category,query.getKeyword(), pageRequest);
        }
        return this.boardRepository.findByIsbnAndBoardCategory(isbn,category, pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<Board> getPaginationBoards(Long isbn, BoardPaginationQuery query) {
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
