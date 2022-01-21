package carrot.challenge.web.login;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    private String password;
}
