package carrot.challenge.domain.user.service;

import carrot.challenge.web.add.AddForm;
import carrot.challenge.domain.user.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Long  save(AddForm addForm);
    Optional<User> findById(Long userId);
    Optional<User> findByEmail(String userEmail);
    Optional<User> findByPhoneNumber(String userPN);
    Optional<User> findByNickName(String userNick);
    String UserInterestItem(Long userId, Long itemId);
    void updateUser(Long userId, User user);
}
