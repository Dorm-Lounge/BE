package dorm.lounge.domain.post.repository;

import dorm.lounge.domain.post.entity.Comment;
import dorm.lounge.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
}
