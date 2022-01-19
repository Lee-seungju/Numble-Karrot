package carrot.challenge.web.comment;

import carrot.challenge.domain.comment.dto.Comment;
import carrot.challenge.domain.comment.service.CommentService;
import carrot.challenge.domain.item.service.ItemService;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class CommentController {

    private final UserService userService;
    private final ItemService itemService;
    private final CommentService commentService;

    @GetMapping("{itemId}/comment")
    public String viewComment(@PathVariable Long itemId,
                              HttpSession session,
                              Model model) {
        Long userId = (Long)session.getAttribute("id");
        if (userId == null || userService.findById(userId).isEmpty() ||
                userService.findById(userId).isEmpty()) {
            needLogin(model);
            return "Message";
        }

        String oldUrl = "/item/" + itemId;

        model.addAttribute("oldUrl",oldUrl);

        Long user_id = itemService.findById(itemId).get().getUser().getUser_id();
        List<Comment> findComments = commentService.findCommentByItemId(itemId);
        List<Comment> sortComments = commentService.sortComments(findComments);

        log.info("sortComments={}", sortComments);

        model.addAttribute("comments", sortComments);
        model.addAttribute("itemUserId", user_id);
        model.addAttribute("commentForm", new CommentForm());
        return "item/comment";
    }

    @PostMapping("{itemId}/comment")
    public String saveComment(@PathVariable Long itemId,
                              HttpSession session,
                              @Valid CommentForm commentForm,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "item/comment";
        }
        Long userId = (Long) session.getAttribute("id");
        User user = userService.findById(userId).get();
        commentService.saveComment(commentForm, user, itemId);

        redirectAttributes.addAttribute("itemId", itemId);

        return "redirect:/item/{itemId}/comment";
    }

    private void needLogin(Model model) {
        model.addAttribute("message", "로그인이 필요한 화면입니다.");
        model.addAttribute("href", "/user/login");
    }
}
