package carrot.challenge.domain.thumbnail.repository;

import carrot.challenge.domain.thumbnail.dto.Thumbnail;

import java.util.List;

public interface ThumbnailRepository {
    Thumbnail save(Thumbnail thumbnail);
    List<Thumbnail> findAll();
    void update(Thumbnail updateParam);
    void remove(Thumbnail thumbnail);
}
