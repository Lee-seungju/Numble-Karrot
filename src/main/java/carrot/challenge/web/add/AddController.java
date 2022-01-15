package carrot.challenge.web.add;

import carrot.challenge.domain.user.service.UserService;
import carrot.challenge.web.validation.AddValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class AddController {

    private final UserService userService;
    private final AddValidation addValidation;

    @GetMapping("add")
    public String addUserForm(Model model) {
        model.addAttribute("addForm", new AddForm());
        return "user/addForm";
    }

    @PostMapping("add")
    public String addUser(@ModelAttribute AddForm addForm,
                                BindingResult bindingResult,
                                HttpSession session,
                                HttpServletResponse response) {

        addValidation.validate(addForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "user/addForm";
        }

        Long userId = userService.save(addForm);

        // 쿠키 세팅
        Cookie setCookie = new Cookie("id", Long.toString(userId));
        setCookie.setMaxAge(60*60*24*30);
        response.addCookie(setCookie);

        session.setAttribute("id", userId);

        return "redirect:/user";
    }
}
