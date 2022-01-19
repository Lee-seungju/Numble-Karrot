package carrot.challenge.web.item;

import carrot.challenge.domain.Interest.service.InterestService;
import carrot.challenge.domain.item.service.ItemService;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
public class InterestController {

    private final InterestService interestService;

    @PostMapping("/interest/{itemId}/add")
    public String addInterest(@PathVariable Long itemId,
                              HttpSession session) {
        Long userId = (Long)session.getAttribute("id");
        interestService.addInterest(itemId, userId);
        return "ok";
    }

    @PostMapping("/interest/{itemId}/delete")
    public String deleteInterest(@PathVariable Long itemId,
                              HttpSession session) {
        Long userId = (Long)session.getAttribute("id");
        interestService.deleteInterest(itemId, userId);
        return "ok";
    }

}
