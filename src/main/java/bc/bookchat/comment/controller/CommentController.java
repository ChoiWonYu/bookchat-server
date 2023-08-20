package bc.bookchat.comment.controller;

import bc.bookchat.comment.controller.dto.CommentCreateRequest;
import bc.bookchat.comment.dto.CommentUpdateRequest;
import bc.bookchat.comment.entity.Comment;
import bc.bookchat.comment.service.CommentService;
import bc.bookchat.common.annotation.TokenInfo;
import bc.bookchat.common.response.ResponseHandler;
import bc.bookchat.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/boards/{boardId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Object> createComment(@PathVariable UUID boardId, @TokenInfo Member member,@Valid @RequestBody CommentCreateRequest commentCreateRequest) {
        Comment comment = commentService.createComment(boardId, member, commentCreateRequest.getContent());
        return ResponseHandler.generateResponse("댓글이 생성되었습니다.", HttpStatus.CREATED, comment.toDto());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Object> editComment(@PathVariable Long commentId, @TokenInfo Member member, @Valid @RequestBody CommentUpdateRequest request){
        Comment comment=commentService.editComment(commentId,member, request.getContent());
        return ResponseHandler.generateResponse("댓글이 수정되었습니다.",HttpStatus.OK,comment.toDto());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long commentId, @TokenInfo Member member){
        Comment comment=commentService.deleteComment(commentId,member);
        return ResponseHandler.generateResponse("댓글이 삭제되었습니다.",HttpStatus.OK,comment.toDto());
    }
}
