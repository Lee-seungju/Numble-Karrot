package carrot.challenge.domain.comment.repository;

import carrot.challenge.domain.comment.dto.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(Long commentId);
    List<Comment> findAllByItemId(Long itemId);
    List<Comment> findAll();
}
