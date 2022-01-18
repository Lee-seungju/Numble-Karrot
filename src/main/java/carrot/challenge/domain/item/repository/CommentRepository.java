package carrot.challenge.domain.item.repository;

import carrot.challenge.domain.item.dto.Comment;
import carrot.challenge.domain.item.dto.Item;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);
    Optional<Comment> findById(Long commentId);
    List<Comment> findAllByItemId(Long itemId);
    List<Comment> findAll();
}
