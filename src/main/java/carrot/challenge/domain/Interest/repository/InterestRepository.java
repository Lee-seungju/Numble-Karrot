package carrot.challenge.domain.Interest.repository;

import carrot.challenge.domain.Interest.dto.Interest;

import java.util.List;
import java.util.Optional;

public interface InterestRepository {
    Interest save(Interest interest);
    void delete(Interest interest);
    Optional<Interest> findByUserIdItemId(Long userId, Long itemId);
    List<Interest> findByUserId(Long userId);
}
