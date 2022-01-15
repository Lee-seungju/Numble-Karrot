package carrot.challenge.web.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
public class ItemForm {

    @NotBlank(message = "카테고리 선택은 필수 입니다.")
    private String category;

    @NotBlank(message = "제목 입력은 필수입니다.")
    private String name;

    @Range(min = 1000, max = 1000000, message = "가격은 최소 1,000원 최대 100,000원으로 해주세요.")
    @PositiveOrZero(message = "머냐이거")
    private Integer price;

    @NotBlank(message = "내용 입력은 필수입니다.")
    private String main;

    private List<MultipartFile> imageFiles;
}
