package carrot.challenge.web.validation;

import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.service.UserService;
import carrot.challenge.web.login.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginValidation implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginForm loginForm = (LoginForm) target;

        if (!StringUtils.hasText(loginForm.getEmail())) {
            errors.rejectValue("email", "required");
            return;
        }
        if (!StringUtils.hasText(loginForm.getPassword())) {
            errors.rejectValue("password", "required");
            return;
        }

        if (StringUtils.hasText(loginForm.getEmail())) {
            Optional<User> findUser = userService.findByEmail(loginForm.getEmail());
            if (findUser.isEmpty())
                errors.rejectValue("email", "noEmail");
            else if (!findUser.get().getPassword().equals(loginForm.getPassword()))
                errors.rejectValue("password", "noPassword");

        }
    }
}
