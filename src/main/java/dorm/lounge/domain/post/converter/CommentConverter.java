package dorm.lounge.domain.post.converter;

import dorm.lounge.domain.post.dto.CommentDTO.CommentResponse.CreateCommentResponse;
import dorm.lounge.domain.post.entity.Comment;
import dorm.lounge.domain.post.entity.Post;
import dorm.lounge.domain.user.entity.User;

public class CommentConverter {

    public static Comment toComment(User user, Post post, String content) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .build();
    }

    public static CreateCommentResponse toCreateCommentResponse(Comment comment) {
        return CreateCommentResponse.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .nickname(comment.getUser().getNickname())
                .build();
    }
}
