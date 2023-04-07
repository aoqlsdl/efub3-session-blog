package efub.session.blog.account.controller;

import efub.session.blog.account.domain.Account;
import efub.session.blog.account.dto.AccountResponseDto;
import efub.session.blog.account.dto.AccountUpdateRequestDto;
import efub.session.blog.account.dto.SignUpRequestDto;
import efub.session.blog.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public AccountResponseDto signUp(@RequestBody @Valid final SignUpRequestDto requestDto) {
        Long id = accountService.signUp(requestDto);
        Account findAccount = accountService.findAccountById(id);
        return AccountResponseDto.from(findAccount);
    }

    @GetMapping("/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public AccountResponseDto getAccount(@PathVariable Long accountId) {
        Account findAccount = accountService.findAccountById(accountId);
        return AccountResponseDto.from(findAccount);
    }

    @PatchMapping("/profile/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public AccountResponseDto update(@PathVariable final Long accountId, @RequestBody @Valid final AccountUpdateRequestDto requestDto)  {
        Long id = accountService.update(accountId, requestDto);
        Account findAccount = accountService.findAccountById(id);
        return AccountResponseDto.from(findAccount);
    }

    @PatchMapping("/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String withdraw(@PathVariable long accountId) {
        accountService.withdraw(accountId);
        return "휴면 계정으로 전환되었습니다";
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String delete(@PathVariable long accountId) {
        accountService.delete(accountId);
        return "탈퇴가 성공적으로 완료되었습니다.";
    }
}
