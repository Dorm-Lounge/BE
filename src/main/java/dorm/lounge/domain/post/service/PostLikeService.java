package dorm.lounge.domain.post.service;

public interface PostLikeService {
    void likePost(String userId, Long postId);
    void unlikePost(String userId, Long postId);
}
