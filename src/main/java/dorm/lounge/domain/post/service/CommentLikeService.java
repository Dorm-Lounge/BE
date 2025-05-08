package dorm.lounge.domain.post.service;

public interface CommentLikeService {
    void likeComment(String userId, Long commentId);
    void unlikeComment(String userId, Long commentId);
}
