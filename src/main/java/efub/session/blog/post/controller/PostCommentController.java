package efub.session.blog.domain.post.controller;

import efub.session.blog.domain.comment.domain.Comment;
import efub.session.blog.domain.comment.dto.CommentRequestDto;
import efub.session.blog.domain.comment.dto.CommentResponseDto;
import efub.session.blog.domain.comment.service.CommentService;
import efub.session.blog.domain.post.dto.response.PostCommentsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// url: /posts/{postId}/comments
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class PostCommentController {

    // 의존관계 : PostCommentController -> CommentService
    private final CommentService commentService;

    // 특정 게시글의 댓글 생성
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    // @Valid -> not null 검증
    public CommentResponseDto createPostComment(@PathVariable Long postId, @RequestBody @Valid CommentRequestDto requestDto) {
        Long commentId = commentService.createComment(postId, requestDto);
        Comment comment = commentService.findCommentById(commentId);
        return CommentResponseDto.of(comment);
    }

    // 특정 게시글의 댓글 목록 조회
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public PostCommentsResponseDto readPostComments(@PathVariable Long postId) {
        List<Comment> commentList = commentService.findCommentListByPost(postId);
        return PostCommentsResponseDto.of(postId, commentList);
    }

}
