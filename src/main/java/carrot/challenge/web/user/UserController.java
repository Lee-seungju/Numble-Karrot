package carrot.challenge.web.user;

import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ModelAndView user(@CookieValue(value = "id", required = false) Long userId, Model model) {
        ModelAndView mav = new ModelAndView();
        log.info("cookies={}", userId);

        // 쿠키값에 따라서 현재 로그인을 한 상태인지 아닌지 확인한다.
        // 데이터베이스 연결이 되어야 확실히 구동되는 파트
        if (userId == null) {
            needLogin(mav);
        } else {
            mav.setViewName("user/userForm");
            User user = userService.findById(userId).get();
            mav.addObject("user", user);
        }
        return mav;
    }

    void needLogin(ModelAndView mav) {
        mav.setViewName("Message");
        mav.addObject("message", "로그인이 필요한 화면입니다.");
        mav.addObject("href", "/user/login");
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        User user1 = new User();
        User user2 = new User();
        user1.setEmail("slee2");
        user1.setPassword("slee2");
        user1.setNickname("slee2");
        user1.setPhone_number("slee2");
        user1.setUsername("slee2");
        user2.setEmail("a");
        user2.setPassword("a");
        user2.setNickname("a");
        user2.setPhone_number("a");
        user2.setUsername("a");
        User user3 = new User();
        user3.setEmail("b");
        user3.setPassword("b");
        user3.setNickname("b");
        user3.setPhone_number("b");
        user3.setUsername("b");
        userService.justSave(user1);
        userService.justSave(user2);
        userService.justSave(user3);
    }
}
