package dorm.lounge.domain.post.repository;

import dorm.lounge.domain.post.entity.Comment;
import dorm.lounge.domain.post.entity.CommentLike;
import dorm.lounge.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUserAndComment(User user, Comment comment);
    void deleteByUserAndComment(User user, Comment comment);
    long countByComment(Comment comment);
}
