package carrot.challenge.web.add;

import lombok.Data;

@Data
public class AddForm {
    private String email;
    private String password;
    private String username;
    private String phone_number;
    private String nickname;
}
