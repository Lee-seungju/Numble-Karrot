package carrot.challenge.web.mypage;

import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.upload.FileStore;
import carrot.challenge.domain.upload.dto.UploadFile;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.service.UserService;
import carrot.challenge.web.validation.EditUserValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class EditUserController {

    private final UserService userService;
    private final FileStore userFileStore;
    private final EditUserValidation editUserValidation;

    @GetMapping("/edit")
    public String editUser(@CookieValue(value = "id", required = false) Long userId,
                           Model model) {
        log.info("getMapping - /user/edit");
        if (userId == null || userService.findById(userId).isEmpty()) {
            needLogin(model);
            return "Message";
        }
        MyPageForm myPageForm = setMyPageForm(userId, new MyPageForm());
        model.addAttribute("myPageForm", myPageForm);
        return "user/editUser";
    }

    private void needLogin(Model model) {
        model.addAttribute("message", "로그인이 필요한 화면입니다.");
        model.addAttribute("href", "/user/login");
    }

    private MyPageForm setMyPageForm(Long userId, MyPageForm myPageForm) {
        User user = userService.findById(userId).get();
        myPageForm.setNickname(user.getNickname());
        myPageForm.setStoreFileName(user.getStoreFileName());
        myPageForm.setUploadFileName(user.getUploadFileName());
        myPageForm.setId(user.getUser_id());
        return myPageForm;
    }

    @PostMapping("/edit")
    public String changeUserInfo(@Valid MyPageForm myPageForm,
                                 BindingResult bindingResult) throws IOException {
        editUserValidation.validate(myPageForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "user/editUser";
        }

        UploadFile attachUserFile = null;
        if (myPageForm.getImageFile() != null)
            attachUserFile = userFileStore.storeFile(myPageForm.getImageFile());

        log.info("attachUserFile={}", attachUserFile);

        User user = setUser(myPageForm, attachUserFile);
        userService.updateUser(user.getUser_id(), user);

       return "redirect:/user";
    }

    private User setUser(MyPageForm myPageForm, UploadFile attachUserFile) {
        User user  = userService.findById(myPageForm.getId()).get();
        if (attachUserFile != null) {
            user.setStoreFileName(attachUserFile.getStoreFileName());
            user.setUploadFileName(attachUserFile.getUploadFileName());
        }
        user.setNickname(myPageForm.getNickname());
        return user;
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + userFileStore.getFullPath(filename));
    }
}
