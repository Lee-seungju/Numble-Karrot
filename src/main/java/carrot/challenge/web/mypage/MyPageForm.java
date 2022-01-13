package carrot.challenge.web.mypage;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class MyPageForm {

    @NotEmpty
    private Long id;
    @NotEmpty
    private String nickname;
    private MultipartFile imageFile;
    private String uploadFileName;
    private String storeFileName;
}
