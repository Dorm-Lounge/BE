package dorm.lounge.domain.post.service;

import dorm.lounge.domain.post.converter.PostConverter;
import dorm.lounge.domain.post.dto.PostDTO.PostRequest.UpdatePostRequest;
import dorm.lounge.domain.post.dto.PostDTO.PostRequest.CreatePostRequest;
import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostResponse;
import dorm.lounge.domain.post.entity.Post;
import dorm.lounge.domain.post.repository.PostRepository;
import dorm.lounge.domain.user.entity.User;
import dorm.lounge.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public GetPostResponse createPost(String userId, CreatePostRequest request) {
        User user = getVerifiedUser(userId);

        Post post = PostConverter.toPost(user, request.getTitle(), request.getContent());
        postRepository.save(post);

        return PostConverter.toPostResponse(user.getNickname(), post);
    }

    @Override
    @Transactional
    public void updatePost(String userId, Long postId, UpdatePostRequest request) {
        User user = getVerifiedUser(userId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        if (!post.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("작성자만 수정할 수 있습니다.");
        }

        post.update(request.getTitle(), request.getContent());
    }

    @Override
    @Transactional
    public void deletePost(String userId, Long postId) {
        User user = getVerifiedUser(userId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        if (!post.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    // GPS 인증 여부 확인 공통 메서드
    private User getVerifiedUser(String userId) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        if (user.getGpsVerified() == null || !user.getGpsVerified()) {
            throw new RuntimeException("GPS 인증이 필요합니다.");
        }

        return user;
    }
}