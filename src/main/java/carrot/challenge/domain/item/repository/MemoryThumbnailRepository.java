package carrot.challenge.domain.item.repository;

import carrot.challenge.domain.item.dto.Thumbnail;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryThumbnailRepository implements ThumbnailRepository{

    private static final Map<Long, Thumbnail> data = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Thumbnail save(Thumbnail thumbnail) {
        thumbnail.setThumbnail_id(++sequence);
        data.put(thumbnail.getThumbnail_id(), thumbnail);
        return thumbnail;
    }

    @Override
    public List<Thumbnail> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(Thumbnail updateParam) {
    }
}
