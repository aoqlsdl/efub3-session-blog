package efub.session.blog.account.service;

import efub.session.blog.account.domain.Account;
import efub.session.blog.account.dto.AccountResponseDto;
import efub.session.blog.account.dto.AccountUpdateRequestDto;
import efub.session.blog.account.dto.SignUpRequestDto;
import efub.session.blog.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @BeforeEach
    void beforeEach() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("create account")
    public void testSignUp() {
        // given
        String email = "email@naver.com";
        String password = "password123!";
        String nickname = "nickname1";
        SignUpRequestDto requestDto = SignUpRequestDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();

        // when
        Long accountId = accountService.signUp(requestDto);

        // then
        Account account = accountRepository.findById(accountId).orElse(null);
        assertNotNull(account);
        assertEquals(requestDto.getEmail(), account.getEmail());
        assertEquals(requestDto.getNickname(), account.getNickname());
    }

    @Test
    @DisplayName("update account")
    public void testUpdate() {
        // given
        Account account = Account.builder()
                .email("email@naver.com")
                .bio("hi")
                .password("password123!")
                .nickname("nickname1")
                .build();

        accountRepository.save(account);
        Long accountId = account.getAccountId();

        String bio = "new bio";
        String nickname = "newnickname";
        AccountUpdateRequestDto requestDto = AccountUpdateRequestDto.builder()
                .bio(bio)
                .nickname(nickname)
                .build();

        // when
        Long updatedAccountId = accountService.update(accountId, requestDto);

        // then
        assertEquals(accountId, updatedAccountId);
        Account updatedAccount = accountRepository.findById(accountId).orElse(null);
        assertNotNull(updatedAccount);
        assertEquals(requestDto.getNickname(), updatedAccount.getNickname());
    }

    @Test
    @DisplayName("delete account")
    public void testWithdrawal() {
        // given
        Account account = Account.builder()
                .email("email@naver.com")
                .bio("hi")
                .password("password123!")
                .nickname("nickname1")
                .build();
        accountRepository.save(account);
        Long accountId = account.getAccountId();

        // when
        accountService.withdraw(accountId);

        // then
        Account deletedAccount = accountRepository.findById(accountId).orElse(null);
        assertNotNull(deletedAccount);
    }
}