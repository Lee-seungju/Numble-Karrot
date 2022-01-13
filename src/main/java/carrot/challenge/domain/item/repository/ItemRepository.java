package carrot.challenge.domain.item.repository;

import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.user.dto.User;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);
    Optional<Item> findById(Long itemId);
    Optional<Item> findByName(String itemName);
    Optional<Item> findByUserId(Long userId);
    Optional<Item> findByCategoryId(Long categoryId);
    List<Item> findAll();
    void update(Long itemId, Item updateParam);
}