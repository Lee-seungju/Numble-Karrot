package carrot.challenge.domain.item.service;

import carrot.challenge.domain.item.dto.Comment;
import carrot.challenge.domain.item.dto.Interest;
import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.dto.Thumbnail;
import carrot.challenge.domain.item.repository.*;
import carrot.challenge.domain.upload.dto.UploadFile;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.repository.DBUserRepository;
import carrot.challenge.web.item.ItemForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final DBItemRepository dbItemRepository;
    private final DBThumbnailRepository dbThumbnailRepository;
    private final CommentRepository commentRepository;
    private final InterestRepository interestRepository;

    @Override
    public Long save(List<UploadFile> imageFiles, ItemForm itemForm, User user, Long categoryId) {


        Item item = Item.builder()
                .user(user)
                .category_id(categoryId)
                .name(itemForm.getName())
                .main(itemForm.getMain())
                .price(itemForm.getPrice())
                .date(LocalDate.now().toString())
                .status(0)
                .storeFileName(imageFiles.get(0).getStoreFileName())
                .uploadFileName(imageFiles.get(0).getUploadFileName())
                .build();
        dbItemRepository.save(item);

        for (UploadFile imageFile : imageFiles) {
            Thumbnail thumbnail = Thumbnail.builder()
                    .storeFileName(imageFile.getStoreFileName())
                    .uploadFileName(imageFile.getUploadFileName())
                    .item_id(item.getItem_id())
                    .build();
            dbThumbnailRepository.save(thumbnail);
        }

        return item.getItem_id();
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        return dbItemRepository.findById(itemId);
    }

    @Override
    public Optional<Item> findByName(String itemName) {
        return dbItemRepository.findByName(itemName);
    }

    @Override
    public List<Item> findAll() {
        return dbItemRepository.findAll();
    }

    @Override
    public List<Item> findAllWithoutUser(Long userId) {
        List<Item> all = dbItemRepository.findAll();
        List<Item> items = new ArrayList<>();
        for (Item item : all) {
            if (item.getUser().getUser_id() != userId)
                items.add(item);
        }
        return items;
    }

    @Override
    public List<Item> findAllWithUser(Long userId) {
        return dbItemRepository.findAllByUserId(userId);
    }

    @Override
    public List<Item> findFourItemsWithUser(Long userId) {
        List<Item> all = dbItemRepository.findAll();
        List<Item> items = new ArrayList<>();
        for (Item item : all) {
            if (item.getUser().getUser_id() == userId)
                items.add(item);
            if (items.size() == 4)
                return items;
        }
        return items;
    }

    @Override
    public void updateItem(Item item) {
        dbItemRepository.update(item);
    }

    @Override
    public List<Thumbnail> getThumbnail(Long itemId) {
        List<Thumbnail> result = new ArrayList<>();
        List<Thumbnail> all = dbThumbnailRepository.findAll();
        for (Thumbnail thumbnail : all) {
            if (thumbnail.getItem_id() == itemId)
                result.add(thumbnail);
        }
        return result;
    }

    @Override
    public List<Comment> findCommentByItemId(Long itemId) {
        return commentRepository.findAllByItemId(itemId);
    }

    @Override
    public void addInterest(Long itemId, Long userId) {
        Interest interest = new Interest();
        interest.setItem_id(itemId);
        interest.setUser_id(userId);
        interestRepository.save(interest);
    }

    @Override
    public void deleteInterest(Long itemId, Long userId) {
        Optional<Interest> findInterest = interestRepository.findByUserIdItemId(userId, itemId);
        if (findInterest.isEmpty())
            return;
        interestRepository.delete(findInterest.get());
    }
}
