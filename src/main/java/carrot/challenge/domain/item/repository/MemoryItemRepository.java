package carrot.challenge.domain.item.repository;

import carrot.challenge.domain.item.dto.Item;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class MemoryItemRepository implements ItemRepository {

    private static final Map<Long, Item> data = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Item save(Item item) {
        item.setItem_id(++sequence);
        data.put(item.getItem_id(), item);
        return item;
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        return Optional.ofNullable(data.get(itemId));
    }

    @Override
    public Optional<Item> findByName(String itemName) {
        return findAll().stream()
                .filter(m -> m.getName().equals(itemName))
                .findFirst();
    }

    @Override
    public Optional<Item> findByUserId(Long userId) {
        return findAll().stream()
                .filter(m -> m.getUser().getUser_id().equals(userId))
                .findFirst();
    }

    @Override
    public Optional<Item> findByCategoryId(Long categoryId) {
        return findAll().stream()
                .filter(m -> m.getCategory_id().equals(categoryId))
                .findFirst();
    }

    @Override
    public List<Item> findAllByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(Item updateParam) {
    }

    public void clearStore() {
        data.clear();
    }
}
