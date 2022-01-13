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
        item.setId(++sequence);
        data.put(item.getId(), item);
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
                .filter(m -> m.getUser_id().equals(userId))
                .findFirst();
    }

    @Override
    public Optional<Item> findByCategoryId(Long categoryId) {
        return findAll().stream()
                .filter(m -> m.getCategory_id().equals(categoryId))
                .findFirst();
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(Long itemId, Item updateParam) {
        Item getItem = findById(itemId).get();
        getItem.setUser_id(updateParam.getUser_id());
        getItem.setCategory_id(updateParam.getCategory_id());
        getItem.setMain(updateParam.getMain());
        getItem.setName(updateParam.getName());
        getItem.setPrice(updateParam.getPrice());
        getItem.setStatus(updateParam.getStatus());
        getItem.setInterest(updateParam.getInterest());
        getItem.setDate(updateParam.getDate());
    }

    public void clearStore() {
        data.clear();
    }
}
