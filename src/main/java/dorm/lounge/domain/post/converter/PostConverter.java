package dorm.lounge.domain.post.converter;

import dorm.lounge.domain.post.dto.PostDTO.PostResponse.GetPostResponse;
import dorm.lounge.domain.post.entity.Post;
import dorm.lounge.domain.user.entity.User;

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
}
