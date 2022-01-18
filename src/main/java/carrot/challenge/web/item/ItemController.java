package carrot.challenge.web.item;

import carrot.challenge.domain.category.dto.Category;
import carrot.challenge.domain.category.service.CategoryService;
import carrot.challenge.domain.item.dto.Comment;
import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.dto.Thumbnail;
import carrot.challenge.domain.item.service.ItemService;
import carrot.challenge.domain.upload.FileStore;
import carrot.challenge.domain.upload.dto.UploadFile;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.service.UserService;
import carrot.challenge.web.validation.AddItemValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final AddItemValidation addItemValidation;
    private final CategoryService categoryService;
    private final ItemService itemService;
    private final UserService userService;
    private final FileStore fileStore;

    @GetMapping("/new")
    public String item(HttpSession session,
                       HttpServletRequest request,
                       Model model) {
        Long userId = (Long)session.getAttribute("id");
        log.info("itemController");
        log.info("userId={}", userId);
        if (userId == null || userService.findById(userId).isEmpty()) {
            needLogin(model);
            return "Message";
        }

        String referer = request.getHeader("REFERER");
        model.addAttribute("oldUrl",referer);

        List<Category> categories = categoryService.cateList();
        model.addAttribute("itemForm", new ItemForm());
        model.addAttribute("selectCate", categories);
        log.info("categories={}", categories);
        log.info("userId={}", userId);
        return "/item/newForm";
    }

    @PostMapping("/new")
    public String addItem(HttpSession session,
                          @Valid ItemForm itemForm,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          Model model) throws IOException {
        log.info("=== /item/new Post ===");
        log.info("category={}",itemForm.getCategory());
        log.info("main={}", itemForm.getMain());
        log.info("name={}", itemForm.getName());
        log.info("price={}", itemForm.getPrice());
        addItemValidation.validate(itemForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            List<Category> categories = categoryService.cateList();
            model.addAttribute("selectCate", categories);
            return "item/newForm";
        }

        Long userId = (Long)session.getAttribute("id");
        User user = userService.findById(userId).get();

        List<UploadFile> itemImageFile = fileStore.storeFiles(itemForm.getImageFiles());

        Long categoryId = categoryService.findCategoryId(itemForm.getCategory());

        Long itemId = itemService.save(itemImageFile, itemForm, user, categoryId);

        log.info("--- 테스트 ---");
        Item item = itemService.findById(itemId).get();
        log.info("date={}",item.getDate());
        log.info("itemCate={}", categoryService.findById(item.getCategory_id()));
        log.info("main={}", item.getMain());
        log.info("price={}", item.getPrice());
        log.info("name={}", item.getName());

        redirectAttributes.addAttribute("itemId", itemId);

        return "redirect:/item/{itemId}";
    }

    private void needLogin(Model model) {
        model.addAttribute("message", "로그인이 필요한 화면입니다.");
        model.addAttribute("href", "/user/login");
    }

    @GetMapping("{itemId}")
    public String viewItem(@PathVariable Long itemId,
                           HttpSession session,
                           HttpServletRequest request,
                           Model model) {
        Long userId = (Long)session.getAttribute("id");
        if (userId == null || userService.findById(userId).isEmpty() ||
                userService.findById(userId).isEmpty()) {
            needLogin(model);
            return "Message";
        }

        String referer = request.getHeader("REFERER");
        model.addAttribute("oldUrl",referer);

        log.info("itemId={}", itemId);
        Item item = itemService.findById(itemId).get();
        String interest = userService.UserInterestItem(userId, itemId);

        String categoryName = categoryService.findById(item.getCategory_id());
        log.info("category={}", categoryName);

        log.info("=== 아이템 가져옴 ===");
        List<Thumbnail> thumbnails = itemService.getThumbnail(itemId);
        List<Item> fourItemsWithUser = itemService.findFourItemsWithUser(item.getUser().getUser_id());
        model.addAttribute("category", categoryName);
        model.addAttribute("item", item);
        model.addAttribute("thumbnails", thumbnails);
        model.addAttribute("diffItems", fourItemsWithUser);
        model.addAttribute("interest", interest);

        return "/item/viewItem";
    }

    @GetMapping("{itemId}/comment")
    public String viewComment(@PathVariable Long itemId,
                              HttpSession session,
                              HttpServletRequest request,
                              Model model) {
        Long userId = (Long)session.getAttribute("id");
        if (userId == null || userService.findById(userId).isEmpty() ||
                userService.findById(userId).isEmpty()) {
            needLogin(model);
            return "Message";
        }

        String referer = request.getHeader("REFERER");
        model.addAttribute("oldUrl",referer);

        List<Comment> comments = itemService.findCommentByItemId(itemId);
        model.addAttribute("comments", comments);
        return "/item/comment";
    }

    @GetMapping("/user/{userId}")
    public String otherItem(@PathVariable Long userId,
                            HttpServletRequest request,
                            Model model) {
        List<Item> items = itemService.findAllWithUser(userId);

        String referer = request.getHeader("REFERER");
        model.addAttribute("oldUrl",referer);

        model.addAttribute("items", items);
        return "/item/viewOtherItem";
    }
}
