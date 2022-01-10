package carrot.challenge.user.dto;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String password;
    private String username;
    private String phone_number;
    private String nickname;
}
