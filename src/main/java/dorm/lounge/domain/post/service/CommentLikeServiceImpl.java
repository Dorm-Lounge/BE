package dorm.lounge.domain.post.service;

import dorm.lounge.domain.post.entity.Comment;
import dorm.lounge.domain.post.entity.CommentLike;
import dorm.lounge.domain.post.repository.CommentLikeRepository;
import dorm.lounge.domain.post.repository.CommentRepository;
import dorm.lounge.domain.user.entity.User;
import dorm.lounge.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    @Transactional
    public void likeComment(String userId, Long commentId) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글 없음"));

        if (!commentLikeRepository.existsByUserAndComment(user, comment)) {
            CommentLike like = CommentLike.builder().user(user).comment(comment).build();
            commentLikeRepository.save(like);
        }
    }

    @Override
    @Transactional
    public void unlikeComment(String userId, Long commentId) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글 없음"));

        commentLikeRepository.deleteByUserAndComment(user, comment);
    }
}
