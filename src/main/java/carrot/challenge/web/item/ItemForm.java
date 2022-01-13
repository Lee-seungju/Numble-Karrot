package carrot.challenge.web.item;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class ItemForm {

    @NotEmpty
    private String category;
    @NotEmpty
    private String name;
    @NotEmpty
    private Long price;
    @NotEmpty
    private String main;
    private List<MultipartFile> imageFiles;
}
