package carrot.challenge.domain.item.service;

import carrot.challenge.domain.item.dto.Comment;
import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.dto.Thumbnail;
import carrot.challenge.domain.upload.dto.UploadFile;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.web.item.ItemForm;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Long save(List<UploadFile> imageFiles, ItemForm itemForm, User user, Long categoryId);
    Optional<Item> findById(Long itemId);
    Optional<Item> findByName(String itemName);
    List<Item> findAll();
    List<Item> findAllWithoutUser(Long userId);
    List<Item> findAllWithUser(Long userId);
    List<Item> findFourItemsWithUser(Long userId);
    void updateItem(Item item);
    List<Thumbnail> getThumbnail(Long itemId);
    List<Comment> findCommentByItemId(Long itemId);
    void addInterest(Long itemId, Long userId);
    void deleteInterest(Long itemId, Long userId);
}