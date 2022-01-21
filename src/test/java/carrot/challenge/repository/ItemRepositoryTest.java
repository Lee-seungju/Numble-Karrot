package carrot.challenge.repository;

import carrot.challenge.domain.item.dto.Item;
import carrot.challenge.domain.item.repository.DBItemRepository;
import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class ItemRepositoryTest {

    @Autowired
    DBItemRepository dbItemRepository;

    @Test
    public void 저장_잘_되는지() {
        //given
        Item item = Item.builder()
                .price(10000)
                .main("a")
                .status(0)
                .name("a")
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .comments(new HashSet<>())
                .interests(new HashSet<>())
                .user(User.builder()
                        .user_id(2L)
                        .email("user2@a.com")
                        .password("user2")
                        .username("user2")
                        .phone_number("01022345678")
                        .nickname("user2")
                        .build())
                .build();

        //when
        Item save = dbItemRepository.save(item);

        //then
        assertThat(item.getPrice()).isEqualTo(save.getPrice());
        assertThat(item.getName()).isEqualTo(save.getName());
        assertThat(item.getMain()).isEqualTo(save.getMain());
    }

    @Test
    public void findById() {
        //given
        Item item = Item.builder()
                .price(10000)
                .main("a")
                .status(0)
                .name("a")
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .comments(new HashSet<>())
                .interests(new HashSet<>())
                .user(User.builder()
                        .user_id(2L)
                        .email("user2@a.com")
                        .password("user2")
                        .username("user2")
                        .phone_number("01022345678")
                        .nickname("user2")
                        .build())
                .build();
        Item save = dbItemRepository.save(item);

        //when
        Item find = dbItemRepository.findById(save.getItem_id()).get();

        //then
        assertThat(item.getPrice()).isEqualTo(find.getPrice());
        assertThat(item.getName()).isEqualTo(save.getName());
        assertThat(item.getMain()).isEqualTo(save.getMain());
    }

    @Test
    public void findByName() {
        //given
        Item item = Item.builder()
                .price(10000)
                .main("a")
                .status(0)
                .name("a")
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .comments(new HashSet<>())
                .interests(new HashSet<>())
                .user(User.builder()
                        .user_id(2L)
                        .email("user2@a.com")
                        .password("user2")
                        .username("user2")
                        .phone_number("01022345678")
                        .nickname("user2")
                        .build())
                .build();
        Item save = dbItemRepository.save(item);

        //when
        Item find = dbItemRepository.findByName(save.getName()).get();

        //then
        assertThat(item.getPrice()).isEqualTo(find.getPrice());
        assertThat(item.getMain()).isEqualTo(save.getMain());
    }

    @Test
    public void findByUserId() {
        //given
        Item item = Item.builder()
                .price(10000)
                .main("a")
                .status(0)
                .name("a")
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .comments(new HashSet<>())
                .interests(new HashSet<>())
                .user(User.builder()
                        .user_id(1L)
                        .email("user2@a.com")
                        .password("user2")
                        .username("user2")
                        .phone_number("01022345678")
                        .nickname("user2")
                        .build())
                .build();
        Item save = dbItemRepository.save(item);

        //when
        Item find = dbItemRepository.findByUserId(save.getUser().getUser_id()).get();

        //then
        assertThat(item.getPrice()).isEqualTo(find.getPrice());
        assertThat(item.getMain()).isEqualTo(save.getMain());
    }

    @Test
    public void findAll() {
        //given
        List<Item> before = dbItemRepository.findAll();
        Item item = Item.builder()
                .price(10000)
                .main("a")
                .status(0)
                .name("a")
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .comments(new HashSet<>())
                .interests(new HashSet<>())
                .user(User.builder()
                        .user_id(2L)
                        .email("user2@a.com")
                        .password("user2")
                        .username("user2")
                        .phone_number("01022345678")
                        .nickname("user2")
                        .build())
                .build();
        dbItemRepository.save(item);

        //when
        List<Item> after = dbItemRepository.findAll();

        //then
        assertThat(after.size()-before.size()).isEqualTo(1);
    }

    @Test
    public void findAllByUserId() {
        //given
        List<Item> before = dbItemRepository.findAllByUserId(1L);
        Item item = Item.builder()
                .price(10000)
                .main("a")
                .status(0)
                .name("a")
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .comments(new HashSet<>())
                .interests(new HashSet<>())
                .user(User.builder()
                        .user_id(2L)
                        .email("user2@a.com")
                        .password("user2")
                        .username("user2")
                        .phone_number("01022345678")
                        .nickname("user2")
                        .build())
                .build();
        dbItemRepository.save(item);

        //when
        List<Item> after = dbItemRepository.findAllByUserId(1L);

        //then
        assertThat(after.size()-before.size()).isEqualTo(0);
    }

    @Test
    public void update() {
        //given
        Item item = Item.builder()
                .price(10000)
                .main("a")
                .status(0)
                .name("a")
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .comments(new HashSet<>())
                .interests(new HashSet<>())
                .user(User.builder()
                        .user_id(2L)
                        .email("user2@a.com")
                        .password("user2")
                        .username("user2")
                        .phone_number("01022345678")
                        .nickname("user2")
                        .build())
                .build();
        Item save = dbItemRepository.save(item);
        Item updateItem = Item.builder()
                .item_id(save.getItem_id())
                .price(10000)
                .main("b")
                .status(0)
                .name("b")
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .comments(new HashSet<>())
                .interests(new HashSet<>())
                .user(User.builder()
                        .user_id(2L)
                        .email("user2@a.com")
                        .password("user2")
                        .username("user2")
                        .phone_number("01022345678")
                        .nickname("user2")
                        .build())
                .build();

        //when
        dbItemRepository.update(updateItem);

        //then
        assertThat(save.getMain()).isEqualTo(updateItem.getMain());
        assertThat(save.getName()).isEqualTo(updateItem.getMain());
    }

    @Test
    public void remove() {
        //given
        List<Item> before = dbItemRepository.findAll();
        Item item = Item.builder()
                .price(10000)
                .main("a")
                .status(0)
                .name("a")
                .date(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .comments(new HashSet<>())
                .interests(new HashSet<>())
                .user(User.builder()
                        .user_id(2L)
                        .email("user2@a.com")
                        .password("user2")
                        .username("user2")
                        .phone_number("01022345678")
                        .nickname("user2")
                        .build())
                .build();
        Item save = dbItemRepository.save(item);

        //when
        dbItemRepository.remove(save);
        List<Item> after = dbItemRepository.findAll();

        //then
        assertThat(after.size()-before.size()).isEqualTo(0);
    }
}
