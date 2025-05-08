package dorm.lounge.domain.post.converter;

import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetComment;
import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostSearchResponse;
import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostListResponse;
import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostResponse;
import dorm.lounge.domain.post.entity.Comment;
import dorm.lounge.domain.post.entity.Post;
import dorm.lounge.domain.user.entity.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
                .createdAt(formatTime(post.getCreatedAt()))
                .isLiked(false)
                .build();
    }

    public static GetPostResponse toPostList(Post post, boolean isLiked) {
        return GetPostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUser().getNickname())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .createdAt(formatTime(post.getCreatedAt()))
                .isLiked(isLiked)
                .commentCount(post.getComments().size())
                .build();
    }

    public static GetPostResponse toPostDetail(Post post, boolean isLiked, List<GetComment> comments) {
        return GetPostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUser().getNickname())
                .likeCount(post.getLikeCount())
                .viewCount(post.getViewCount())
                .createdAt(formatTime(post.getCreatedAt()))
                .isLiked(isLiked)
                .comments(comments)
                .commentCount(post.getComments().size())
                .build();
    }

    public static GetComment toComment(Comment comment, boolean isLiked, int likeCount) {
        return GetComment.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .createdAt(formatTime(comment.getCreatedAt()))
                .isLiked(isLiked)
                .likeCount(likeCount)
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

    public static GetPostListResponse toPostListResponse(List<GetPostResponse> posts, List<GetPostResponse> bestPosts) {
        return GetPostListResponse.builder()
                .posts(posts)
                .bestPosts(bestPosts)
                .build();
    }

    public static GetPostSearchResponse toPostSearchResponse(List<GetPostResponse> posts) {
        return GetPostSearchResponse.builder()
                .posts(posts)
                .build();
    }
}
