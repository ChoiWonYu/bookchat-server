package bc.bookchat.comment.service;

import bc.bookchat.board.entity.Board;
import bc.bookchat.board.service.BoardService;
import bc.bookchat.comment.repository.CommentRepository;
import bc.bookchat.comment.entity.Comment;
import bc.bookchat.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardService boardService;
    private final CommentRepository commentRepository;

    public Comment createComment(UUID boardId, Member member, String content) {
        Board board=boardService.getBoardDetail(boardId);
        Comment comment = new Comment(content,member,board);

        comment.addToBoard(board);

        commentRepository.save(comment);
        return comment;
    }
}
