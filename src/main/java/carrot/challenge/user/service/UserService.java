package carrot.challenge.user.service;

import carrot.challenge.user.dto.AddForm;
import carrot.challenge.user.dto.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserService {
    void    justSave(User user);
    Long  save(AddForm addForm, ModelAndView mav);
    User    findById(Long userId);
    Long  login(String email, String password, ModelAndView mav);
}
