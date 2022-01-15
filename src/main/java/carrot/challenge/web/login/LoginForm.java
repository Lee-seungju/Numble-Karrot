package carrot.challenge.web.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

    @NotBlank(message = "빈칸, 공백은 허용되지 않습니다.")
    private String email;

    @NotBlank(message = "빈칸, 공백은 허용되지 않습니다.")
    private String password;
}
