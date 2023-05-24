package efub.session.blog.domain.follow.service;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.service.AccountService;
import efub.session.blog.domain.follow.domain.Follow;
import efub.session.blog.domain.follow.dto.FollowRequestDto;
import efub.session.blog.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor // final이 붙은 필드의 생성자를 자동으로 생성
public class FollowService {
    // 의존관계 : FollowService->FollowRepository
    private final FollowRepository followRepository;
    // 의존관계 : FollowService->AccountService
    public final AccountService accountService;

    // 팔로우 추가
    public Long add(Long accountId, FollowRequestDto followRequestDto) {
        Account follower = accountService.findAccountById(accountId);
        Account following = accountService.findAccountById(followRequestDto.getFollowingId());
        Follow follow = followRepository.save(followRequestDto.toEntity(follower, following));
        return follow.getFollowId();
    }


    // 팔로우 여부
    @Transactional(readOnly = true)
    public boolean isFollowing(Long followerId, Long followingId) {
        Account follower = accountService.findAccountById(followerId);
        Account following = accountService.findAccountById(followingId);
        return followRepository.existsByFollowerAndFollowing(follower, following);
    }

    // 팔로워 목록
    @Transactional(readOnly = true)
    public List<Follow> findAllByFollowerId(Long accountId) {
        Account findAccount = accountService.findAccountById(accountId);
        return followRepository.findAllByFollower(findAccount);
    }


    // 팔로잉 목록
    @Transactional(readOnly = true)
    public List<Follow> findAllByFollowingId(Long accountId) {
        Account findAccount = accountService.findAccountById(accountId);
        return followRepository.findAllByFollowing(findAccount);
    }

    // 팔로우 취소
    @Transactional(readOnly = true)
    public Follow findByFollowerIdAndFollowingId(Long followerId, Long followingId) {
        Account follower = accountService.findAccountById(followerId);
        Account following = accountService.findAccountById(followingId);
        return followRepository.findByFollowerAndFollowing(follower, following);
    }

    public void delete(Long accountId, Long followingId) {
        Follow findFollow = findByFollowerIdAndFollowingId(accountId, followingId);
        followRepository.deleteByFollowId(findFollow.getFollowId());
    }

}
