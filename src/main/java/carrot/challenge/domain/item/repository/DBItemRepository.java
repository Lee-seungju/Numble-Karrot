package carrot.challenge.domain.item.repository;

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
public class DBItemRepository implements ItemRepository{

    private final EntityManager em;

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        Item item = em.find(Item.class, itemId);
        return Optional.ofNullable(item);
    }

    @Override
    public Optional<Item> findByName(String itemName) {
        List<Item> result = em.createQuery("select u from Item u where u.name =: name", Item.class)
                .setParameter("name", itemName)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Item> findByUserId(Long userId) {
        List<Item> result = em.createQuery("select u from Item u where u.user.user_id =: user_id", Item.class)
                .setParameter("user_id", userId)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Item> findByCategoryId(Long categoryId) {
        List<Item> result = em.createQuery("select u from Item u where u.category_id =: category_id", Item.class)
                .setParameter("category_id", categoryId)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Item> findAllByUserId(Long userId) {
         return em.createQuery("select u from Item u where u.user.user_id =: user_id", Item.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Item> findAll() {
        return em.createQuery("select m from Item m", Item.class)
                .getResultList();
    }

    @Override
    public void update(Item updateParam) {
        em.merge(updateParam);
    }

    @Override
    public void remove(Item item) {
        em.remove(item);
    }
}
