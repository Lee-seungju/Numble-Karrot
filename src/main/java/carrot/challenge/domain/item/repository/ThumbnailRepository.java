package carrot.challenge.domain.item.repository;

import carrot.challenge.domain.item.dto.Thumbnail;

import java.util.List;
import java.util.Optional;

public interface ThumbnailRepository {
    Thumbnail save(Thumbnail thumbnail);
    Optional<Thumbnail> findById(Long itemId);
    List<Thumbnail> findAll();
    void update(Long itemId, Thumbnail updateParam);
}
