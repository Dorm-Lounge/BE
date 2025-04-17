package dorm.lounge.domain.post.repository;

import dorm.lounge.domain.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
