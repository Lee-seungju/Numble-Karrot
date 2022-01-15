package carrot.challenge.web.add;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class AddForm {

    @NotBlank(message = "빈칸, 공백은 허용되지 않습니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "빈칸, 공백은 허용되지 않습니다.")
    private String password;

    @NotBlank(message = "빈칸, 공백은 허용되지 않습니다.")
    private String username;

    @NotBlank(message = "빈칸, 공백은 허용되지 않습니다.")
    private String phone_number;

    @NotBlank(message = "빈칸, 공백은 허용되지 않습니다.")
    private String nickname;
}
