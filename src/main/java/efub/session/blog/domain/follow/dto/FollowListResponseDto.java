package efub.session.blog.domain.follow.dto;

import efub.session.blog.domain.follow.domain.Follow;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FollowListResponseDto {
    // list, count 필드
    private List<FollowListResponseDto.SingleFollower> followerList;
    private List<FollowListResponseDto.SingleFollowing> followingList;
    private Integer followerCount;
    private Integer followingCount;

    // 팔로워 한 명
    @Getter
    public static class SingleFollower {
        private Long followId;
        private String followerNickname;
        private String email;

        public SingleFollower(Follow follow) {
            this.followId = follow.getFollowId();
            this.followerNickname = follow.getFollower().getNickname();
            this.email = follow.getFollower().getEmail();
            this.email = follow.getFollower().getEmail();
        }

        public static FollowListResponseDto.SingleFollower of(Follow follow) {
            return new FollowListResponseDto.SingleFollower(follow);
        }
    }

    // 팔로잉 한 명
    @Getter
    public static class SingleFollowing {
        private Long followId;
        private String followingNickname;
        private String email;

        public SingleFollowing(Follow follow) {
            this.followId = follow.getFollowId();
            this.followingNickname = follow.getFollower().getNickname();
            this.email = follow.getFollowing().getEmail();
        }

        public static FollowListResponseDto.SingleFollowing of(Follow follow) {
            return new FollowListResponseDto.SingleFollowing(follow);
        }
    }

    // 팔로우 목록, 개수
    public static FollowListResponseDto of(List<Follow> followers , List<Follow> followings) {
        return FollowListResponseDto.builder()
                .followerList(followers.stream().map(FollowListResponseDto.SingleFollower::of).collect(Collectors.toList()))
                .followingList(followings.stream().map(FollowListResponseDto.SingleFollowing::of).collect(Collectors.toList()))
                .followerCount(followers.size())
                .followingCount(followings.size())
                .build();
    }
}