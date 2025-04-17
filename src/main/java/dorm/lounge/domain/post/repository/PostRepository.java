package dorm.lounge.domain.post.repository;

import dorm.lounge.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findTop3ByCreatedAtAfterOrderByLikeCountDesc(LocalDateTime dateTime);
}
