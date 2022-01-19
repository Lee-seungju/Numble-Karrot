package carrot.challenge.domain.Interest.dto;

import carrot.challenge.domain.user.dto.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interest_id;
    private Long user_id;
    private Long item_id;
}
