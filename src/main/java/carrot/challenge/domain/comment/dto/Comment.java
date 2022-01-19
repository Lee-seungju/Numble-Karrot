package carrot.challenge.domain.comment.dto;

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
    private String date;
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
