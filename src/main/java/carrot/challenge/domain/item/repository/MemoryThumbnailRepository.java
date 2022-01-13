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
        thumbnail.setId(++sequence);
        data.put(thumbnail.getId(), thumbnail);
        return thumbnail;
    }

    @Override
    public Optional<Thumbnail> findById(Long itemId) {
        return findAll().stream()
                .filter(m -> m.getItem_id().equals(itemId))
                .findFirst();
    }

    @Override
    public List<Thumbnail> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(Long itemId, Thumbnail updateParam) {
        Thumbnail findThumbnail = findById(itemId).get();
        findThumbnail.setStoreFileName(updateParam.getStoreFileName());
        findThumbnail.setUploadFileName(updateParam.getUploadFileName());
    }
}
