package dorm.lounge.domain.post.controller;

import dorm.lounge.domain.post.dto.CommentDTO.CommentRequest.CreateCommentRequest;
import dorm.lounge.domain.post.dto.CommentDTO.CommentResponse.CreateCommentResponse;
import dorm.lounge.domain.post.service.CommentService;
import dorm.lounge.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "Comment 컨트롤러", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    @Operation(summary = "댓글 등록", description = "게시글에 댓글을 등록합니다.")
    public ApiResponse<CreateCommentResponse> createComment(
            Authentication authentication,
            @PathVariable Long postId,
            @RequestBody CreateCommentRequest request
    ) {
        return ApiResponse.onSuccess(
                commentService.createComment(authentication.getName(), postId, request)
        );
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    public ApiResponse<String> deleteComment(
            Authentication authentication,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(authentication.getName(), commentId);
        return ApiResponse.onSuccess("댓글 삭제 완료");
    }
}
