package efub.session.blog.domain.account.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountInfoRequestDto { // 로그인 기능이 없어 회원 정보를 받아오는 임시 방편 클래스
    @NotNull(message = "작성자는 필수로 입력되어야 합니다.")
    private Long accountId;


    public AccountInfoRequestDto(Long accountId) {
        this.accountId = accountId;
    }
}
