package carrot.challenge.domain.item.dto;

import carrot.challenge.domain.Interest.dto.Interest;
import carrot.challenge.domain.comment.dto.Comment;
import carrot.challenge.domain.thumbnail.dto.Thumbnail;
import carrot.challenge.domain.user.dto.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long item_id;
    private Long category_id;
    private String name;
    private String date;
    private Integer price;
    private String main;
    private int status;
    private String uploadFileName;
    private String storeFileName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    Set<Thumbnail> thumbnails;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    Set<Comment> comments;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    Set<Interest> interests;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
