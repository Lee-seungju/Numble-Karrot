package carrot.challenge.domain.category.repository;

import carrot.challenge.domain.category.dto.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class DBCategoryRepository implements CategoryRepository {

    private final EntityManager em;

    @Override
    public List<Category> cateList() {
        return em.createQuery("select m from Category m", Category.class)
                .getResultList();
    }

    @Override
    public Optional<Category> findById(Long cateId) {
        Category category = em.find(Category.class, cateId);
        return Optional.ofNullable(category);
    }

    @Override
    public Optional<Category> findByName(String cateName) {
        List<Category> result = em.createQuery("select u from Category u where u.name =: name", Category.class)
                .setParameter("name", cateName)
                .getResultList();
        return result.stream().findAny();
    }
}
