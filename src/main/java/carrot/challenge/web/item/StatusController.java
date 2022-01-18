package carrot.challenge.web.item;

import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.service.ItemService;
import carrot.challenge.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class StatusController {

    private final ItemService itemService;
    private final UserService userService;

    @GetMapping("{itemId}/status/{status}")
    public String changeStatus(@PathVariable Long itemId,
                               @PathVariable Integer status,
                               HttpSession session,
                               Model model) {
        Long userId = (Long)session.getAttribute("id");
        if (userId == null || userService.findById(userId).isEmpty()) {
            needLogin(model);
            return "Message";
        }
        Item item = itemService.findById(itemId).get();
        item.setStatus(status);
        itemService.updateItem(item);
        return "redirect:/user/item";
    }

    private void needLogin(Model model) {
        model.addAttribute("message", "로그인이 필요한 화면입니다.");
        model.addAttribute("href", "/user/login");
    }
}
