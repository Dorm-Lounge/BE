package dorm.lounge.domain.post.controller;

import dorm.lounge.domain.post.service.PostLikeService;
import dorm.lounge.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/like")
@Tag(name = "Post 좋아요 컨트롤러", description = "게시글 좋아요 및 좋아요 취소 API")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    @Operation(summary = "게시글 좋아요", description = "해당 게시글에 좋아요를 누릅니다.")
    public ApiResponse<String> likePost(Authentication authentication, @PathVariable Long postId) {
        postLikeService.likePost(authentication.getName(), postId);
        return ApiResponse.onSuccess("좋아요가 등록되었습니다.");
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 좋아요 취소", description = "해당 게시글의 좋아요를 취소합니다.")
    public ApiResponse<String> unlikePost(Authentication authentication, @PathVariable Long postId) {
        postLikeService.unlikePost(authentication.getName(), postId);
        return ApiResponse.onSuccess("좋아요가 취소되었습니다.");
    }
}
