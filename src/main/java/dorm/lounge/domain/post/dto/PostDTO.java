package dorm.lounge.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PostDTO {
    public static class PostRequest {
        @Getter
        @NoArgsConstructor
        public static class CreatePostRequest {
            private String title;
            private String content;
        }

        @Getter
        @NoArgsConstructor
        public static class UpdatePostRequest {
            private String title;
            private String content;
        }
    }

    public static class PostResponse {

        @Getter
        @Builder
        public static class GetPostListResponse {
            private List<GetPostResponse> posts;
            private List<GetPostResponse> bestPosts;
        }

        @Getter
        @Builder
        public static class GetPostResponse {
            private Long postId;
            private String title;
            private String content;
            private String nickname;
            private int likeCount;
            private int viewCount;
            private String createdAt;
            private boolean isLiked;
            private int commentCount;
            private List<GetComment> comments;
        }

        @Getter
        @Builder
        public static class GetComment {
            private Long commentId;
            private String content;
            private String createdAt;
            private int likeCount;
            private boolean isLiked;
        }

        @Getter
        @Builder
        public static class GetPostSearchResponse {
            private List<GetPostResponse> posts;
        }
    }
}
