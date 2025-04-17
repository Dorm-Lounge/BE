package dorm.lounge.domain.post.repository;

import dorm.lounge.domain.post.entity.Post;
import dorm.lounge.domain.post.entity.PostLike;
import dorm.lounge.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserAndPost(User user, Post post);
    Optional<PostLike> findByUserAndPost(User user, Post post);
}
