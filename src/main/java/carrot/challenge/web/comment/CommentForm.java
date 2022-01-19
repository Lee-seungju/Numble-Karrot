package carrot.challenge.web.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentForm {

    @NotBlank(message = "내용 입력은 필수입니다.")
    private String comment;
}
