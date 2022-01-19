package carrot.challenge.domain.Interest.service;

import carrot.challenge.domain.item.dto.Item;

import java.util.List;

public interface InterestService {
    void addInterest(Long itemId, Long userId);
    void deleteInterest(Long itemId, Long userId);
    List<Item> findByInterest(Long userId);
}
