package dorm.lounge.domain.post.controller;


import dorm.lounge.domain.post.dto.PostDTO.PostRequest.UpdatePostRequest;
import dorm.lounge.domain.post.dto.PostDTO.PostRequest.CreatePostRequest;
import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostResponse;
import dorm.lounge.domain.post.service.PostService;
import dorm.lounge.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "Post 컨트롤러", description = "Post 관련 API")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "게시글 등록", description = "게시글을 새로 등록합니다.")
    public ApiResponse<GetPostResponse> createPost(Authentication authentication,
                                                        @RequestBody CreatePostRequest request) {
        GetPostResponse response = postService.createPost(authentication.getName(), request);
        return ApiResponse.onSuccess(response);
    }

    @PutMapping("/{postId}")
    @Operation(summary = "게시글 수정", description = "작성자가 게시글을 수정합니다.")
    public ApiResponse<String> updatePost(Authentication authentication,
                                          @PathVariable Long postId,
                                          @RequestBody UpdatePostRequest request) {
        postService.updatePost(authentication.getName(), postId, request);
        return ApiResponse.onSuccess("게시글이 수정되었습니다.");
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제", description = "작성자가 게시글을 삭제합니다.")
    public ApiResponse<String> deletePost(Authentication authentication,
                                          @PathVariable Long postId) {
        postService.deletePost(authentication.getName(), postId);
        return ApiResponse.onSuccess("게시글이 삭제되었습니다.");
    }

}
