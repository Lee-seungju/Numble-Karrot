package carrot.challenge.domain.comment.service;

import carrot.challenge.domain.comment.dto.Comment;
import carrot.challenge.domain.comment.repository.CommentRepository;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.web.comment.CommentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findCommentByItemId(Long itemId) {
        return commentRepository.findAllByItemId(itemId);
    }

    @Override
    public void saveComment(CommentForm commentForm, User user, Long itemId) {
        Comment comment = new Comment();

        comment.setItem_id(itemId);
        comment.setUser(user);
        comment.setContents(commentForm.getComment());
        comment.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        commentRepository.save(comment);
    }

    @Override
    public List<Comment> sortComments(List<Comment> comments) {
        Collections.sort(comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime ldt1 = LocalDateTime.parse(o1.getDate(), formatter);
                LocalDateTime ldt2 = LocalDateTime.parse(o2.getDate(), formatter);
                if (ldt1.isAfter(ldt2))
                    return 1;
                return 0;
            }
        });
        return comments;
    }
}
