package carrot.challenge.domain.user.dto;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String email;

    private String password;

    private String username;

    private String phone_number;

    private String nickname;

    private String uploadFileName;

    private String storeFileName;
}
