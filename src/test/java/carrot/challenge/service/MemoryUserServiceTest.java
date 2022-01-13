package carrot.challenge.service;

import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.repository.MemoryUserRepository;
import carrot.challenge.domain.user.service.MemoryUserService;
import carrot.challenge.web.add.AddForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class MemoryUserServiceTest {

    MemoryUserRepository memoryUserRepository;
    MemoryUserService memoryUserService;

    @BeforeEach
    public void beforeEach() {
        memoryUserRepository = new MemoryUserRepository();
        memoryUserService = new MemoryUserService(memoryUserRepository);
    }

    @AfterEach
    public void afterEach() {
        memoryUserRepository.clearStore();
    }

    @Test
    public void 회원가입() {
        //given
        AddForm user = new AddForm();
        user.setEmail("user1");

        //when
        Long saveId = memoryUserService.save(user);

        //then
        User result = memoryUserService.findById(saveId).get();
        assertThat(result.getEmail()).isEqualTo("user1");
    }

    @Test
    public void 중복회원_에러_메시지() {
        //given
        AddForm user1 = new AddForm();
        user1.setEmail("user1");
        user1.setNickname("a");
        user1.setPhone_number("b");

        AddForm user2 = new AddForm();
        user2.setEmail("user1");

        AddForm user3 = new AddForm();
        user3.setNickname("a");

        AddForm user4 = new AddForm();
        user4.setPhone_number("b");

        //when
        memoryUserService.save(user1);
        IllegalStateException e1 = assertThrows(IllegalStateException.class,
                () -> memoryUserService.save(user2));
        IllegalStateException e2 = assertThrows(IllegalStateException.class,
                () -> memoryUserService.save(user3));
        IllegalStateException e3 = assertThrows(IllegalStateException.class,
                () -> memoryUserService.save(user4));

        //then
        assertThat(e1.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        assertThat(e2.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        assertThat(e3.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}
