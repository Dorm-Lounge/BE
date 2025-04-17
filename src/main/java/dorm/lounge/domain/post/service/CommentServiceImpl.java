package dorm.lounge.domain.post.service;

import dorm.lounge.domain.post.converter.CommentConverter;
import dorm.lounge.domain.post.dto.CommentDTO.CommentRequest.CreateCommentRequest;
import dorm.lounge.domain.post.dto.CommentDTO.CommentResponse.CreateCommentResponse;
import dorm.lounge.domain.post.entity.Comment;
import dorm.lounge.domain.post.entity.Post;
import dorm.lounge.domain.post.repository.CommentRepository;
import dorm.lounge.domain.post.repository.PostRepository;
import dorm.lounge.domain.user.entity.User;
import dorm.lounge.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CreateCommentResponse createComment(String userId, Long postId, CreateCommentRequest request) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        Comment comment = CommentConverter.toComment(user, post, request.getContent());
        commentRepository.save(comment);

        return CommentConverter.toCreateCommentResponse(comment);
    }

    @Override
    @Transactional
    public void deleteComment(String userId, Long commentId) {
        User user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글 없음"));

        if (!comment.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}
