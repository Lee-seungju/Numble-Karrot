package carrot.challenge.domain.comment.service;

import carrot.challenge.domain.comment.dto.Comment;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.web.comment.CommentForm;

import java.util.List;

public interface CommentService {
    List<Comment> findCommentByItemId(Long itemId);
    void saveComment(CommentForm commentForm, User user, Long itemId);
    List<Comment> sortComments(List<Comment> comments);
}
