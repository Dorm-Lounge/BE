package dorm.lounge.domain.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDTO {
    public static class CommentRequest {
        @Getter
        @NoArgsConstructor
        public static class CreateCommentRequest {
            private String content;
        }
    }

    public static class CommentResponse {
        @Getter
        @Builder
        public static class CreateCommentResponse {
            private Long commentId;
            private String content;
            private String nickname;
        }
    }
}
