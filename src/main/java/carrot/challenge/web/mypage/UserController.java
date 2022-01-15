package carrot.challenge.web.mypage;

import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String user(@CookieValue(value = "id", required = false) Long userId,
                       HttpSession session,
                       Model model) {
        log.info("cookies={}", userId);

        // 쿠키값에 따라서 현재 로그인을 한 상태인지 아닌지 확인한다.
        // 데이터베이스 연결이 되어야 확실히 구동되는 파트
        if (userId == null || userService.findById(userId).isEmpty()) {
            needLogin(model);
            return "Message";
        }
        session.setAttribute("id", userId);
        model.addAttribute("user", userService.findById(userId).get());
        return "user/userForm";
    }

    private void needLogin(Model model) {
        model.addAttribute("message", "로그인이 필요한 화면입니다.");
        model.addAttribute("href", "/user/login");
    }
}
