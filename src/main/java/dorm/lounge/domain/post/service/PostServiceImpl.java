package dorm.lounge.domain.post.service;

import dorm.lounge.domain.post.converter.PostConverter;
import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostSearchResponse;
import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostListResponse;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    @Transactional(readOnly = true)
    public GetPostListResponse getAllPosts(String sortType) {
        // 1. 정렬 방식에 따라 게시글 전체 조회
        List<Post> posts;
        if ("popular".equalsIgnoreCase(sortType)) {
            posts = postRepository.findAllByOrderByLikeCountDesc();
        } else {
            posts = postRepository.findAllByOrderByCreatedAtDesc();
        }

        // 2. 최근 7일 이내 게시글 중 좋아요 수 기준 상위 3개 게시글 조회
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Post> bestPosts = postRepository.findTop3ByCreatedAtAfterOrderByLikeCountDesc(sevenDaysAgo);

        List<GetPostResponse> postList = posts.stream()
                .map(PostConverter::toPostList)
                .collect(Collectors.toList());

        List<GetPostResponse> bestPostList = bestPosts.stream()
                .map(PostConverter::toPostList)
                .collect(Collectors.toList());

        return PostConverter.toPostListResponse(postList, bestPostList);
    }
    @Override
    @Transactional
    public GetPostResponse getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        post.addViewCount(); // 조회 수 증가
        return PostConverter.toPostList(post);
    }

    @Override
    @Transactional(readOnly = true)
    public GetPostSearchResponse searchPosts(String keyword) {
        List<Post> posts = postRepository.findByTitleContainingOrContentContaining(keyword, keyword);
        return PostConverter.toPostSearchResponse(posts);
    }
}