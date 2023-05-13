package efub.session.blog.domain.comment.controller;

import efub.session.blog.domain.comment.domain.Comment;
import efub.session.blog.domain.comment.dto.CommentRequestDto;
import efub.session.blog.domain.comment.dto.CommentResponseDto;
import efub.session.blog.domain.comment.service.CommentHeartService;
import efub.session.blog.domain.comment.service.CommentService;
import efub.session.blog.domain.heart.dto.HeartRequestDto;
import efub.session.blog.domain.post.dto.response.PostCommentsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor    // 생성자를 통한 의존관계 주입
@RequestMapping("/comments/{commentId}")
public class CommentController {

    private final CommentService commentService;    // 의존관계: CommentController -> CommentService
    private final CommentHeartService commentHeartService; // 의존관계: CommentController -> CommentHeartService

    // 특정 게시글의 댓글 목록 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PostCommentsResponseDto readPostComments(@PathVariable Long postId) {
        List<Comment> commentList = commentService.findCommentListByPost(postId);
        return PostCommentsResponseDto.of(postId, commentList);
    }

    // 특정 게시글의 댓글 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createPostComment(@PathVariable Long postId, @RequestBody @Valid CommentRequestDto requestDto) {
        Long commentId = commentService.createComment(postId, requestDto);
        Comment comment = commentService.findCommentById(commentId);
        return CommentResponseDto.of(comment);
    }

    // 댓글 수정
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto updatePostComment(@PathVariable final Long commentId, @RequestBody @Valid final CommentRequestDto requestDto) {
        commentService.updateComment(requestDto, commentId);
        Comment comment = commentService.findCommentById(commentId);
        return CommentResponseDto.of(comment);
    }

    // 댓글 삭제
    // 반환할 데이터가 없으므로 삭제되었다는 메시지 반환
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public String deleteComment(@PathVariable final Long commentId) {
        commentService.deleteComment(commentId);
        return "성공적으로 삭제되었습니다.";
    }

    // 댓글 좋아요
//    @PostMapping("/hearts")
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public String createCommentHeart(@PathVariable final Long commentId, @RequestBody final HeartRequestDto requestDto) {
//        commentHeartService.create(commentId, requestDto.getAccountId());
//        return "좋아요를 눌렀습니다.";
//    }

//    @DeleteMapping("/hearts")
//    @ResponseStatus(value = HttpStatus.OK)
//    public String deleteCommentHeart(@PathVariable final Long commentId, @RequestParam final Long accountId) {
//        commentHeartService.delete(commentId, accountId);
//        return "좋아요가 취소되었습니다.";
//    }
}
