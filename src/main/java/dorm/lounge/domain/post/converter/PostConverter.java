package dorm.lounge.domain.post.converter;

import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostListResponse;
import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostList;
import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostResponse;
import dorm.lounge.domain.post.entity.Post;
import dorm.lounge.domain.user.entity.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class PostConverter {
    public static Post toPost(User user, String title, String content) {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .viewCount(0)
                .likeCount(0)
                .build();
    }

    public static GetPostResponse toPostResponse(String nickname, Post post) {
        return GetPostResponse.builder()
                .nickname(nickname)
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .build();
    }

    public static GetPostList toPostList(Post post) {
        return GetPostList.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUser().getNickname())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .createdAt(formatTime(post.getCreatedAt()))
                .build();
    }

    private static String formatTime(LocalDateTime createdAt) {
        Duration duration = Duration.between(createdAt, LocalDateTime.now());

        if (duration.getSeconds() < 60) return "방금 전";
        if (duration.toMinutes() < 60) return duration.toMinutes() + "분 전";
        if (duration.toHours() < 24) return duration.toHours() + "시간 전";
        if (duration.toDays() < 7) return duration.toDays() + "일 전";
        return createdAt.toLocalDate().toString();
    }

    public static GetPostListResponse toPostListResponse(List<GetPostList> posts, List<GetPostList> bestPosts) {
        return GetPostListResponse.builder()
                .posts(posts)
                .bestPosts(bestPosts)
                .build();
    }
}
