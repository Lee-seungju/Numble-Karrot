package carrot.challenge.web.login;

import carrot.challenge.domain.user.service.UserService;
import carrot.challenge.web.validation.LoginValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final LoginValidation loginValidation;

    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "user/loginForm";
    }

    @PostMapping("login")
    public String login(@Valid LoginForm loginForm,
                        BindingResult bindingResult,
                        HttpSession session,
                        HttpServletResponse response) {

        log.info("errors={}", bindingResult);
        loginValidation.validate(loginForm, bindingResult);
        log.info("errors={}", bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "user/loginForm";
        }

        log.info("errors={}", bindingResult);

        Long userId = userService.findByEmail(loginForm.getEmail()).get().getUser_id();

        Cookie setCookie = new Cookie("id", Long.toString(userId));
        setCookie.setMaxAge(60*60*24*30);
        response.addCookie(setCookie);

        session.setAttribute("id", userId);

        return "redirect:/user";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request,
                         HttpSession session,
                         HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for(Cookie c : cookies)
                if (c.getName().equals("id")) {
                    log.info("id={}", c.getValue());
                    c.setMaxAge(0);
                    response.addCookie(c);
                }
        }
        session.removeAttribute("id");
        return "redirect:/";
    }
}
