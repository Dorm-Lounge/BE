package dorm.lounge.domain.post.service;

import dorm.lounge.domain.post.entity.Comment;
import dorm.lounge.domain.post.entity.CommentLike;
import dorm.lounge.domain.post.repository.CommentLikeRepository;
import dorm.lounge.domain.post.repository.CommentRepository;
import dorm.lounge.domain.user.entity.User;
import dorm.lounge.domain.user.repository.UserRepository;
import dorm.lounge.global.exception.CommentException;
import dorm.lounge.global.status.ErrorStatus;
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
                .orElseThrow(() -> new CommentException(ErrorStatus.AUTH_USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorStatus.COMMENT_NOT_FOUND));

        if (!commentLikeRepository.existsByUserAndComment(user, comment)) {
            CommentLike like = CommentLike.builder().user(user).comment(comment).build();
            commentLikeRepository.save(like);
        }
    }

    @Override
    @Transactional
    public void unlikeComment(String userId, Long commentId) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new CommentException(ErrorStatus.AUTH_USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorStatus.COMMENT_NOT_FOUND));

        commentLikeRepository.deleteByUserAndComment(user, comment);
    }
}
