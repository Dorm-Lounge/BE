package dorm.lounge.domain.post.service;

import dorm.lounge.domain.post.entity.Post;
import dorm.lounge.domain.post.entity.PostLike;
import dorm.lounge.domain.post.repository.PostLikeRepository;
import dorm.lounge.domain.post.repository.PostRepository;
import dorm.lounge.domain.user.entity.User;
import dorm.lounge.domain.user.repository.UserRepository;
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
            throw new RuntimeException("이미 좋아요를 눌렀습니다.");
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
                .orElseThrow(() -> new RuntimeException("좋아요한 기록이 없습니다."));

        postLikeRepository.delete(postLike);
        post.decreaseLike(); // 게시글 likeCount -1
    }

    private User getVerifiedUser(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));
    }
}
