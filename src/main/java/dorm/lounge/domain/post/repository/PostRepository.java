package dorm.lounge.domain.post.repository;

import dorm.lounge.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 최신순 정렬
    List<Post> findAllByOrderByCreatedAtDesc();

    // 인기순 정렬
    List<Post> findAllByOrderByLikeCountDesc();

    // 일주일 내 게시글 중 좋아요 상위 3개
    List<Post> findTop3ByCreatedAtAfterOrderByLikeCountDesc(LocalDateTime after);

    List<Post> findByTitleContainingOrContentContaining(String title, String content);
}
