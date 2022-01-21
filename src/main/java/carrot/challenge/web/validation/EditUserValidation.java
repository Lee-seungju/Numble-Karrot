package carrot.challenge.web.validation;

import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.service.UserService;
import carrot.challenge.web.mypage.MyPageForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class EditUserValidation implements Validator {

    private final UserService userService;


    @Override
    public boolean supports(Class<?> clazz) {
        return MyPageForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MyPageForm myPageForm = (MyPageForm) target;

        if (StringUtils.hasText(myPageForm.getNickname())) {
            Optional<User> findUser = userService.findByNickName(myPageForm.getNickname());
            if (findUser.isPresent() && findUser.get().getUser_id() != myPageForm.getId())
                errors.rejectValue("nickname", "already");
        }
    }
}
