package carrot.challenge.domain.thumbnail.repository;

import carrot.challenge.domain.thumbnail.dto.Thumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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

    @Override
    public void remove(Thumbnail thumbnail) {
        em.remove(thumbnail);
    }


}
