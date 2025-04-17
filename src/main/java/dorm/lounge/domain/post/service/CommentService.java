package dorm.lounge.domain.post.service;

import dorm.lounge.domain.post.dto.CommentDTO.CommentResponse.CreateCommentResponse;
import dorm.lounge.domain.post.dto.CommentDTO.CommentRequest.CreateCommentRequest;

public interface CommentService {
    CreateCommentResponse createComment(String userId, Long postId, CreateCommentRequest request);
    void deleteComment(String userId, Long commentId);
}
