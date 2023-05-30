package efub.session.blog.domain.follow.controller;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.service.AccountService;
import efub.session.blog.domain.follow.domain.Follow;
import efub.session.blog.domain.follow.dto.FollowListResponseDto;
import efub.session.blog.domain.follow.dto.FollowRequestDto;
import efub.session.blog.domain.follow.dto.FollowStatusResponseDto;
import efub.session.blog.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService; // 의존관계: FollowController -> FollowService
    private final AccountService accountService; // 의존관계: FollowController -> AccountService

    // 팔로우 걸기
    @PostMapping("/{accountId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public FollowStatusResponseDto addFollow(@PathVariable final Long accountId, @RequestBody final FollowRequestDto requestDto) {
        Long id = followService.add(accountId, requestDto);
        Boolean isFollow = followService.isFollowing(accountId, requestDto.getFollowingId());
        Account findAccount = accountService.findAccountById(requestDto.getFollowingId());
        return new FollowStatusResponseDto(findAccount, isFollow);
    }

    // 팔로우 목록 조회
    @GetMapping("/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public FollowListResponseDto getFollowList(@PathVariable final Long accountId) {
        List<Follow> followerList = followService.findAllByFollowingId(accountId);
        List<Follow> followingList = followService.findAllByFollowerId(accountId);
        return FollowListResponseDto.of(followerList, followingList);
    }

    // 팔로우 여부 조회
    @GetMapping("/{accountId}/search")
    @ResponseStatus(value = HttpStatus.OK)
    public FollowStatusResponseDto searchAccount(@PathVariable final Long accountId, @RequestParam final String email) {
        Account searchAccount = accountService.findAccountByEmail(email);
        Boolean isFollow = followService.isFollowing(accountId, searchAccount.getAccountId());
        return new FollowStatusResponseDto(searchAccount, isFollow);
    }

    // 팔로우 취소
    @DeleteMapping("/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public FollowStatusResponseDto deleteFollow(@PathVariable final Long accountId, @RequestParam final Long followingId) {
        followService.delete(accountId, followingId);
        Account findAccount = accountService.findAccountById(followingId);
        Boolean isFollow = followService.isFollowing(accountId, followingId);
        return new FollowStatusResponseDto(findAccount, isFollow);
    }

}