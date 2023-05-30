package efub.session.blog.domain.follow.repository;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.follow.domain.Follow;
import efub.session.blog.domain.follow.dto.FollowListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 팔로우 존재 확인
    Boolean existsByFollowerAndFollowing(Account follower, Account following);

    // 팔로우 목록
    List<Follow> findAllByFollower(Account follower);
    List<Follow> findAllByFollowing(Account following);

    // 팔로우 취소
    Follow findByFollowerAndFollowing(Account follower, Account following);

    void deleteByFollowId(Long followId);

}
