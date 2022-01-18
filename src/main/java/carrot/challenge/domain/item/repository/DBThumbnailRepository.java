package carrot.challenge.domain.item.repository;

import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.dto.Thumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class DBThumbnailRepository implements ThumbnailRepository{

    private final EntityManager em;

    @Override
    public Thumbnail save(Thumbnail thumbnail) {
        em.persist(thumbnail);
        return thumbnail;
    }

    @Override
    public List<Thumbnail> findAll() {
        return em.createQuery("select m from Thumbnail m", Thumbnail.class)
                .getResultList();
    }

    @Override
    public void update(Thumbnail updateParam) {
        em.merge(updateParam);
    }
}
