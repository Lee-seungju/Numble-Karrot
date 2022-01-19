package carrot.challenge.domain.item.service;

import carrot.challenge.domain.comment.repository.CommentRepository;
import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.thumbnail.dto.Thumbnail;
import carrot.challenge.domain.item.repository.*;
import carrot.challenge.domain.thumbnail.repository.DBThumbnailRepository;
import carrot.challenge.domain.upload.dto.UploadFile;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.web.item.ItemForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final DBItemRepository dbItemRepository;
    private final DBThumbnailRepository dbThumbnailRepository;
    private final CommentRepository commentRepository;

    @Override
    public Long save(List<UploadFile> imageFiles, ItemForm itemForm, User user, Long categoryId) {

        Item item = Item.builder()
                .user(user)
                .category_id(categoryId)
                .name(itemForm.getName())
                .main(itemForm.getMain())
                .price(itemForm.getPrice())
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
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
    public void updateItemByItemData(List<UploadFile> imageFiles, ItemForm itemForm, Long itemId, Long categoryId) {
        Item item = dbItemRepository.findById(itemId).get();

        Set<Thumbnail> thumbnails = item.getThumbnails();
        for (Thumbnail thumbnail : thumbnails) {
            dbThumbnailRepository.remove(thumbnail);
        }

        item.setMain(itemForm.getMain());
        item.setName(itemForm.getName());
        item.setPrice(itemForm.getPrice());
        item.setStoreFileName(imageFiles.get(0).getStoreFileName());
        item.setUploadFileName(imageFiles.get(0).getUploadFileName());
        item.setCategory_id(categoryId);
        item.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Set<Thumbnail> thumbnailSet = new HashSet<>();

        for (UploadFile imageFile : imageFiles) {
            Thumbnail thumbnail = Thumbnail.builder()
                    .storeFileName(imageFile.getStoreFileName())
                    .uploadFileName(imageFile.getUploadFileName())
                    .item_id(item.getItem_id())
                    .build();
            Thumbnail saveThumbnail = dbThumbnailRepository.save(thumbnail);
            thumbnailSet.add(saveThumbnail);
        }

        item.setThumbnails(thumbnailSet);

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
    public String betweenDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(date, formatter);
        LocalDateTime now = LocalDateTime.now();
        if (ChronoUnit.YEARS.between(ldt, now) != 0) {
            return ChronoUnit.YEARS.between(ldt, now) + "년 전";
        } else if (ChronoUnit.MONTHS.between(ldt, now) != 0) {
            return ChronoUnit.MONTHS.between(ldt, now) + "달 전";
        } else if (ChronoUnit.DAYS.between(ldt, now) != 0) {
            return ChronoUnit.DAYS.between(ldt, now) + "일 전";
        } else if (ChronoUnit.HOURS.between(ldt, now) != 0) {
            return ChronoUnit.HOURS.between(ldt, now) + "시간 전";
        } else if (ChronoUnit.MINUTES.between(ldt, now) != 0) {
            return ChronoUnit.MINUTES.between(ldt, now) + "분 전";
        }
        return ChronoUnit.SECONDS.between(ldt, now) + "초 전";
    }

    @Override
    public List<Item> sortItems(List<Item> items) {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime ldt1 = LocalDateTime.parse(o1.getDate(), formatter);
                LocalDateTime ldt2 = LocalDateTime.parse(o2.getDate(), formatter);
                if (o1.getStatus() < o2.getStatus())
                    return -1;
                else if (ldt1.isAfter(ldt2))
                    return -1;
                return 0;
            }
        });
        return items;
    }

    @Override
    public void removeItem(Long itemId) {
        Item item = findById(itemId).get();
        dbItemRepository.remove(item);
    }
}
