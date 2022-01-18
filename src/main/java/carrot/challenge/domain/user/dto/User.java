package carrot.challenge.domain.user.dto;

import carrot.challenge.domain.item.dto.Comment;
import carrot.challenge.domain.item.dto.Interest;
import carrot.challenge.domain.item.dto.Item;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String email;

    private String password;

    private String username;

    private String phone_number;

    private String nickname;

    private String uploadFileName;

    private String storeFileName;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    Set<Item> items;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    Set<Interest> interests;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    Set<Comment> comments;
}
