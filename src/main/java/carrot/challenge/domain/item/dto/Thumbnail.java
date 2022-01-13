package carrot.challenge.domain.item.dto;

import lombok.Data;

@Data
public class Thumbnail {
    private Long id;
    private Long item_id;
    private String uploadFileName;
    private String storeFileName;
}

