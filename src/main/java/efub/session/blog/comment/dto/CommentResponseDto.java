package efub.session.blog.domain.comment.dto;

import efub.session.blog.domain.comment.domain.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// 애노테이션 추가하기!
@Getter
@Builder(access = AccessLevel.PRIVATE) // 자동적으로 @AllArgsConstructor 추가되는 것
public class CommentResponseDto {

    private Long commentId;
    private Long postId;
    private String writerName;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .writerName(comment.getWriter().getNickname())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .build();
    }
}
