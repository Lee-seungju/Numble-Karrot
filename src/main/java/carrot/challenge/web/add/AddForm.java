package carrot.challenge.web.add;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddForm {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String username;

    @NotEmpty
    private String phone_number;

    @NotEmpty
    private String nickname;
}
