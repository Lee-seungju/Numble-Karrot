package carrot.challenge.repository;

import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.repository.MemoryItemRepository;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.repository.MemoryUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MemoryItemRepositoryTest {

    MemoryItemRepository memoryItemRepository = new MemoryItemRepository();

    @AfterEach
    public void afterEach() {
        memoryItemRepository.clearStore();
    }

    @Test
    public void save() {
        //given
        Item item = new Item();
        item.setName("a");

        //when
        memoryItemRepository.save(item);

        //then
        Item result = memoryItemRepository.findById(item.getId()).get();
        assertThat(result).isEqualTo(item);
    }

    @Test
    public void findByName() {
        //given
        Item item1 = new Item();
        item1.setName("user1");
        memoryItemRepository.save(item1);

        //when
        Item result = memoryItemRepository.findByName("user1").get();

        //then
        assertThat(result).isEqualTo(item1);
    }

    @Test
    public void findByUserId() {
        //given
        Item item1 = new Item();
        item1.setUser_id(1L);
        memoryItemRepository.save(item1);

        //when
        Item result = memoryItemRepository.findByUserId(1L).get();

        //then
        assertThat(result).isEqualTo(item1);
    }

    @Test
    public void findByPhoneNumber() {
        //given
        Item item1 = new Item();
        item1.setCategory_id(1L);
        memoryItemRepository.save(item1);

        //when
        Item result = memoryItemRepository.findByCategoryId(1L).get();

        //then
        assertThat(result).isEqualTo(item1);
    }

    @Test
    public void update() {
        //given
        Item item1 = new Item();
        item1.setName("item1");
        memoryItemRepository.save(item1);

        Item item2 = new Item();
        item2.setName("item2");

        //when
        memoryItemRepository.update(item1.getId(), item2);
        Item result = memoryItemRepository.findById(item1.getId()).get();

        //then
        assertThat(result.getName()).isEqualTo("item2");
    }

    @Test
    public void findAll() {
        //given
        Item item1 = new Item();
        item1.setName("item1");
        memoryItemRepository.save(item1);
        Item item2 = new Item();
        item2.setName("item2");
        memoryItemRepository.save(item2);

        //when
        List<Item> result = memoryItemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }
}
