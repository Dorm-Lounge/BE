package dorm.lounge.domain.post.service;

import dorm.lounge.domain.post.entity.Post;
import dorm.lounge.domain.post.entity.PostLike;
import dorm.lounge.domain.post.repository.PostLikeRepository;
import dorm.lounge.domain.post.repository.PostRepository;
import dorm.lounge.domain.user.entity.User;
import dorm.lounge.domain.user.repository.UserRepository;
import dorm.lounge.global.exception.PostException;
import dorm.lounge.global.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Override
    @Transactional
    public void likePost(String userId, Long postId) {
        User user = getVerifiedUser(userId);
        Post post = getPost(postId);

        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new PostException(ErrorStatus.POST_ALREADY_LIKED);
        }

        postLikeRepository.save(PostLike.builder().user(user).post(post).build());
        post.increaseLike(); // 게시글 likeCount +1
    }

    @Override
    @Transactional
    public void unlikePost(String userId, Long postId) {
        User user = getVerifiedUser(userId);
        Post post = getPost(postId);

        PostLike postLike = postLikeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_LIKE_NOT_FOUND));

        postLikeRepository.delete(postLike);
        post.decreaseLike(); // 게시글 likeCount -1
    }

    private User getVerifiedUser(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new PostException(ErrorStatus.AUTH_USER_NOT_FOUND));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorStatus.POST_NOT_FOUND));
    }
}
