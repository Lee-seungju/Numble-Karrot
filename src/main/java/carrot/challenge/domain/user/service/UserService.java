package carrot.challenge.domain.user.service;

import carrot.challenge.web.add.AddForm;
import carrot.challenge.domain.user.dto.User;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserService {
    void    justSave(User user);
    Long  save(AddForm addForm);
    Optional<User> findById(Long userId);
    Optional<User> findByEmail(String userEmail);
    Optional<User> findByPhoneNumber(String userPN);
    Optional<User> findByNickName(String userNick);
}
