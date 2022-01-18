package carrot.challenge.domain.item.dto;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Thumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long thumbnail_id;

    @Column(name = "item_id")
    private Long item_id;
    private String uploadFileName;
    private String storeFileName;
}

