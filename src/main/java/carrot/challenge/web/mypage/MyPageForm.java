package carrot.challenge.web.mypage;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class MyPageForm {

    private Long id;
    @NotBlank(message = "비어있거나 공백은 입력할 수 없습니다.")
    private String nickname;
    private MultipartFile imageFile;
    private String uploadFileName;
    private String storeFileName;
}
