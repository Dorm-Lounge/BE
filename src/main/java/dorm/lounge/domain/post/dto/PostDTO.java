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
        @AllArgsConstructor
        public static class GetPostResponse {
            private String nickname;
            private Long postId;
            private String title;
            private String content;
            private int viewCount;
            private int likeCount;
        }

        @Getter
        @Builder
        public static class GetPostListResponse {
            private List<GetPostList> posts;
            private List<GetPostList> bestPosts;
        }

        @Getter
        @Builder
        public static class GetPostList {
            private Long postId;
            private String title;
            private String content;
            private String nickname;
            private int likeCount;
            private int viewCount;
            private String createdAt;
        }
    }
}
