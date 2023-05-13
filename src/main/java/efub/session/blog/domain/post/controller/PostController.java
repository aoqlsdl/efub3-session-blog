package efub.session.blog.domain.post.controller;

import efub.session.blog.domain.account.domain.Account;
import efub.session.blog.domain.account.service.AccountService;
import efub.session.blog.domain.heart.dto.HeartRequestDto;
import efub.session.blog.domain.post.domain.Post;
import efub.session.blog.domain.post.dto.request.PostModifyRequestDto;
import efub.session.blog.domain.post.dto.request.PostRequestDto;
import efub.session.blog.domain.post.dto.response.PostResponseDto;
import efub.session.blog.domain.post.service.PostHeartService;
import efub.session.blog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostHeartService postHeartService;
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResponseDto postAdd(@RequestBody PostRequestDto postRequestDto) {
        Post post = postService.addPost(postRequestDto);
        return PostResponseDto.from(post);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<PostResponseDto> postListFind() {
        List<Post> postList = postService.findPostList();
        return postList.stream().map(PostResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto postFind(@PathVariable Long postId, @RequestParam final Long accountId) {
        Post post = postService.findPost(postId);
        boolean isHeart = postHeartService.isHeart(accountId, post);
        Integer heartCount = postHeartService.countPostHeart(post);
        PostResponseDto responseDto = PostResponseDto.from(post);
        responseDto.uploadHeart(heartCount, isHeart);
        return PostResponseDto.from(post);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String postRemove(@PathVariable Long postId, @RequestParam Long accountId) {
        postService.removePost(postId, accountId);
        return "성공적으로 삭제가 완료되었습니다.";
    }

    @PutMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto postModify(@PathVariable Long postId, @RequestBody PostModifyRequestDto requestDto) {
        Post post = postService.modifyPost(postId, requestDto);
        Account account = accountService.findAccountById(requestDto.getAccountId());
        Integer heartCount = postHeartService.countPostHeart(post);
        boolean isHeart = postHeartService.isExistsByWriterAndPost(account, post);
        PostResponseDto responseDto = PostResponseDto.from(post);
        responseDto.uploadHeart(heartCount, isHeart);
        return PostResponseDto.from(post);
    }

    @PostMapping("/{commentId}/hearts")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String createPostHeart(@PathVariable final Long postId, @RequestBody final HeartRequestDto requestDto) {
        postHeartService.create(postId, requestDto.getAccountId());
        return "좋아요를 눌렀습니다.";
    }

    @DeleteMapping("/{commentId}/hearts")
    @ResponseStatus(value = HttpStatus.OK)
    public String deletePostHeart(@PathVariable final Long postId, @RequestParam final Long accountId) {
        postHeartService.delete(postId, accountId);
        return "좋아요가 취소되었습니다.";
    }
}
