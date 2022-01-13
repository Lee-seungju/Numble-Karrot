package carrot.challenge.domain.item.service;

import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.upload.dto.UploadFile;
import carrot.challenge.web.item.ItemForm;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Long save(List<UploadFile> imageFiles, ItemForm itemForm, Long userId, Long categoryId);
    Optional<Item> findById(Long itemId);
    Optional<Item> findByName(Long itemId);
    void updateItem(Long itemId, Item item);
}