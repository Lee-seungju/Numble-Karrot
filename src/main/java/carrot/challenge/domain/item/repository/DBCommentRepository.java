package carrot.challenge.domain.item.repository;

import carrot.challenge.domain.item.dto.Comment;
import carrot.challenge.domain.item.dto.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class DBCommentRepository implements CommentRepository{

    private final EntityManager em;

    @Override
    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }

    @Override
    public Optional<Comment> findById(Long commentId) {
        Comment comment = em.find(Comment.class, commentId);
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> findAllByItemId(Long itemId) {
        return em.createQuery("select u from Item u where u.item_id =: item_id", Comment.class)
                .setParameter("item_id", itemId)
                .getResultList();
    }

    @Override
    public List<Comment> findAll() {
        return em.createQuery("select m from Comment m", Comment.class)
                .getResultList();
    }
}
