package efub.session.blog.domain.comment.repository;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.comment.domain.Comment;
import efub.session.blog.domain.comment.domain.CommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// 3. 의존성 주입
public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    Integer countByComment(Comment comment);
    List<CommentHeart> findByWriter(Account account);
    boolean existsByWriterAndComment(Account account, Comment comment);
    Optional<CommentHeart> findByWriterAndComment(Account account, Comment comment);
}
