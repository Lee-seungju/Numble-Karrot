package carrot.challenge.web.mypage;

import carrot.challenge.domain.Interest.service.InterestService;
import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.service.ItemService;
import carrot.challenge.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user/interest")
@RequiredArgsConstructor
public class InterestListController {

    private final UserService userService;
    private final InterestService interestService;

    @GetMapping
    public String moveInterestList(HttpSession session,
                                   Model model) {
        Long userId = (Long)session.getAttribute("id");
        if (userId == null || userService.findById(userId).isEmpty() ||
                userService.findById(userId).isEmpty()) {
            needLogin(model);
            return "Message";
        }

        List<Item> items = interestService.findByInterest(userId);

        String oldUrl = "/user";
        model.addAttribute("oldUrl",oldUrl);

        model.addAttribute("items", items);
        return "user/interestList";
    }

    private void needLogin(Model model) {
        model.addAttribute("message", "로그인이 필요한 화면입니다.");
        model.addAttribute("href", "/user/login");
    }

}
