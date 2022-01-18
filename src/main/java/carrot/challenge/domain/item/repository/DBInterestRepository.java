package carrot.challenge.domain.item.repository;

import carrot.challenge.domain.item.dto.Interest;
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
public class DBInterestRepository implements InterestRepository{

    private final EntityManager em;

    @Override
    public Interest save(Interest interest) {
        em.persist(interest);
        return interest;
    }

    @Override
    public void delete(Interest interest) {
        em.remove(interest);
    }

    @Override
    public Optional<Interest> findByUserIdItemId(Long userId, Long itemId) {
        List<Interest> result = em.createQuery("select u from Interest u where u.user_id =: user_id and u.item_id =: item_id", Interest.class)
                .setParameter("user_id", userId)
                .setParameter("item_id", itemId)
                .getResultList();
        return result.stream().findAny();
    }
}
