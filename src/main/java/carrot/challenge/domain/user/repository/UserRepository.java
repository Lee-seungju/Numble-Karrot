package carrot.challenge.domain.user.repository;

import carrot.challenge.domain.user.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long userId);
    Optional<User> findByEmail(String userEmail);
    Optional<User> findByNickname(String userNickname);
    Optional<User> findByPhoneNumber(String userPhoneNumber);
    List<User> findAll();
    void update(Long userId, User updateParam);
}
