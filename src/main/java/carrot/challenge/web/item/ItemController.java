package carrot.challenge.web.item;

import carrot.challenge.domain.item.dto.Category;
import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.service.ItemService;
import carrot.challenge.domain.item.service.MemoryCategoryService;
import carrot.challenge.domain.item.service.MemoryItemService;
import carrot.challenge.domain.upload.FileStore;
import carrot.challenge.domain.upload.dto.UploadFile;
import carrot.challenge.domain.user.service.MemoryUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final MemoryCategoryService memoryCategoryService;
    private final MemoryItemService memoryItemService;
    private final MemoryUserService memoryUserService;
    private final FileStore fileStore;

    @GetMapping("/new")
    public String item(@CookieValue(value = "id", required = false) Long userId, Model model) {
        if (userId == null || memoryUserService.findById(userId).isEmpty()) {
            needLogin(model);
            return "Message";
        }
        log.info("userId={}", userId);
        List<Category> categories = memoryCategoryService.cateList();
        model.addAttribute("cate", categories);
        return "/item/newForm";
    }

    @PostMapping("/new")
    public String addItem(@CookieValue(value = "id", required = false) Long userId,
                          @ModelAttribute ItemForm itemForm,
                          RedirectAttributes redirectAttributes) throws IOException {
        List<UploadFile> itemImageFile = fileStore.storeFiles(itemForm.getImageFiles());

        Long categoryId = memoryCategoryService.findCategoryId(itemForm.getCategory());

        Long itemId = memoryItemService.save(itemImageFile, itemForm, userId, categoryId);

        log.info("--- 테스트 ---");
        Item item = memoryItemService.findById(itemId).get();
        log.info("date={}",item.getDate());
        log.info("itemCate={}", memoryCategoryService.findById(item.getCategory_id()));
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
    public String viewItem(@PathVariable Long itemId, Model model) {
        log.info("itemId={}", itemId);
        Item item = memoryItemService.findById(itemId).get();
        model.addAttribute("item", item);
        return "/item/viewItem";
    }

}
