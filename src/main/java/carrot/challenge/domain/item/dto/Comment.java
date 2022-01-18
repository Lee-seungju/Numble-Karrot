package carrot.challenge.domain.item.dto;

import carrot.challenge.domain.user.dto.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;
    private Long item_id;
    private Long user_id;
    private String contents;
}
