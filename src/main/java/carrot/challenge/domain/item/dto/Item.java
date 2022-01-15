package carrot.challenge.domain.item.dto;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private Long user_id;
    private Long category_id;
    private String name;
    private String date;
    private Integer price;
    private String main;
    private Long interest;
    private int status;
}
