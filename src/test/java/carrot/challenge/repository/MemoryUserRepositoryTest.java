package carrot.challenge.repository;

import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.repository.MemoryUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryUserRepositoryTest {

    MemoryUserRepository memoryUserRepository = new MemoryUserRepository();

    @AfterEach
    public void afterEach() {
        memoryUserRepository.clearStore();
    }

    @Test
    public void save() {
        //given
        User user = new User();
        user.setEmail("a");

        //when
        memoryUserRepository.save(user);

        //then
        User result = memoryUserRepository.findById(user.getId()).get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void findByEmail() {
        //given
        User user1 = new User();
        user1.setEmail("user1");
        memoryUserRepository.save(user1);

        //when
        User result = memoryUserRepository.findByEmail("user1").get();

        //then
        assertThat(result).isEqualTo(user1);
    }

    @Test
    public void findByNickName() {
        //given
        User user1 = new User();
        user1.setNickname("user1");
        memoryUserRepository.save(user1);

        //when
        User result = memoryUserRepository.findByNickname("user1").get();

        //then
        assertThat(result).isEqualTo(user1);
    }

    @Test
    public void findByPhoneNumber() {
        //given
        User user1 = new User();
        user1.setPhone_number("user1");
        memoryUserRepository.save(user1);

        //when
        User result = memoryUserRepository.findByPhoneNumber("user1").get();

        //then
        assertThat(result).isEqualTo(user1);
    }

    @Test
    public void update() {
        //given
        User user1 = new User();
        user1.setEmail("user1");
        memoryUserRepository.save(user1);

        User user2 = new User();
        user2.setEmail("user2");

        //when
        memoryUserRepository.update(user1.getId(), user2);
        User result = memoryUserRepository.findById(user1.getId()).get();

        //then
        assertThat(result.getEmail()).isEqualTo("user2");
    }

    @Test
    public void findAll() {
        //given
        User user1 = new User();
        user1.setEmail("user1");
        memoryUserRepository.save(user1);
        User user2 = new User();
        user2.setEmail("user2");
        memoryUserRepository.save(user2);

        //when
        List<User> result = memoryUserRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }
}
