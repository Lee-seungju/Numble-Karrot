package carrot.challenge.web.validation;

import carrot.challenge.domain.user.service.UserService;
import carrot.challenge.web.add.AddForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class AddValidation implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AddForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AddForm addForm = (AddForm) target;

        if (!StringUtils.hasText(addForm.getEmail())) {
            errors.rejectValue("email", "required");
        }
        if (!StringUtils.hasText(addForm.getPassword())) {
            errors.rejectValue("password", "required");
        }
        if (!StringUtils.hasText(addForm.getUsername())) {
            errors.rejectValue("username", "required");
        }
        if (!StringUtils.hasText(addForm.getPhone_number())) {
            errors.rejectValue("phone_number", "required");
        }
        if (!StringUtils.hasText(addForm.getNickname())) {
            errors.rejectValue("nickname", "required");
        }

        if (StringUtils.hasText(addForm.getEmail()) &&
                userService.findByEmail(addForm.getEmail()).isPresent()) {
            errors.rejectValue("email", "already");
        }
        if (StringUtils.hasText(addForm.getPhone_number()) &&
                userService.findByPhoneNumber(addForm.getPhone_number()).isPresent()) {
            errors.rejectValue("phone_number", "already");
        }
        if (StringUtils.hasText(addForm.getNickname()) &&
                userService.findByNickName(addForm.getNickname()).isPresent()) {
            errors.rejectValue("nickname", "already");
        }

    }
}
