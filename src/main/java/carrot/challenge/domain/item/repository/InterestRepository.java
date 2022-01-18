package carrot.challenge.domain.item.repository;

import carrot.challenge.domain.item.dto.Comment;
import carrot.challenge.domain.item.dto.Interest;

import java.util.List;
import java.util.Optional;

public interface InterestRepository {
    Interest save(Interest interest);
    void delete(Interest interest);
    Optional<Interest> findByUserIdItemId(Long userId, Long itemId);
}
