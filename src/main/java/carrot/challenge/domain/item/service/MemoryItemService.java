package carrot.challenge.domain.item.service;

import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.dto.Thumbnail;
import carrot.challenge.domain.item.repository.MemoryItemRepository;
import carrot.challenge.domain.item.repository.MemoryThumbnailRepository;
import carrot.challenge.domain.upload.dto.UploadFile;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.web.item.ItemForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoryItemService implements ItemService {

    private final MemoryItemRepository memoryItemRepository;
    private final MemoryThumbnailRepository memoryThumbnailRepository;

    @Override
    public Long save(List<UploadFile> imageFiles, ItemForm itemForm, Long userId, Long categoryId) {
        Item saveItem = saveItem(itemForm, userId, categoryId);
        saveThumbnail(imageFiles, saveItem.getId());
        return saveItem.getId();
    }

    void saveThumbnail(List<UploadFile> imageFiles, Long itemId) {
        for (UploadFile imageFile : imageFiles) {
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setItem_id(itemId);
            thumbnail.setStoreFileName(imageFile.getStoreFileName());
            thumbnail.setUploadFileName(imageFile.getUploadFileName());
            memoryThumbnailRepository.save(thumbnail);
        }
    }

    Item saveItem(ItemForm itemForm, Long userId, Long categoryId) {
        Item item = new Item();
        item.setUser_id(userId);
        item.setCategory_id(categoryId);
        item.setName(itemForm.getName());
        item.setMain(itemForm.getMain());
        item.setPrice(itemForm.getPrice());
        item.setInterest(0L);
        item.setDate(LocalDate.now().toString());
        item.setStatus(0);
        return memoryItemRepository.save(item);
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        return Optional.empty();
    }

    @Override
    public Optional<Item> findByName(Long itemId) {
        return Optional.empty();
    }

    @Override
    public void updateItem(Long itemId, Item item) {

    }
}
