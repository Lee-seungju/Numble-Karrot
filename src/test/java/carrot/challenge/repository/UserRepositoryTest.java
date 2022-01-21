package carrot.challenge.repository;

import carrot.challenge.domain.user.dto.User;
import carrot.challenge.domain.user.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void 저장_잘_되는지() {
        //given
        User user = User.builder()
                .email("user1@a.com")
                .password("user1")
                .username("user1")
                .phone_number("01012345678")
                .nickname("user1")
                .build();

        //when
        User saveUser = userRepository.save(user);

        //then
        assertThat(user.getUsername()).isEqualTo(saveUser.getUsername());
        assertThat(user.getEmail()).isEqualTo(saveUser.getEmail());
        assertThat(user.getPassword()).isEqualTo(saveUser.getPassword());
        assertThat(user.getPhone_number()).isEqualTo(saveUser.getPhone_number());
        assertThat(user.getNickname()).isEqualTo(saveUser.getNickname());
    }

    @Test
    public void 아이디_잘_찾는지() {
        //given
        User user = User.builder()
                .email("user1@a.com")
                .password("user1")
                .username("user1")
                .phone_number("01012345678")
                .nickname("user1")
                .build();
        User saveUser = userRepository.save(user);

        //when
        User findUser = userRepository.findById(saveUser.getUser_id()).get();

        //then
        assertThat(user.getUsername()).isEqualTo(findUser.getUsername());
        assertThat(user.getEmail()).isEqualTo(findUser.getEmail());
        assertThat(user.getPassword()).isEqualTo(findUser.getPassword());
        assertThat(user.getPhone_number()).isEqualTo(findUser.getPhone_number());
        assertThat(user.getNickname()).isEqualTo(findUser.getNickname());
    }

    @Test
    public void 이메일_잘_찾는지() {
        //given
        User user = User.builder()
                .email("user1@a.com")
                .password("user1")
                .username("user1")
                .phone_number("01012345678")
                .nickname("user1")
                .build();
        userRepository.save(user);

        //when
        User findUser = userRepository.findByEmail("user1@a.com").get();

        //then
        assertThat(user.getUsername()).isEqualTo(findUser.getUsername());
        assertThat(user.getEmail()).isEqualTo(findUser.getEmail());
        assertThat(user.getPassword()).isEqualTo(findUser.getPassword());
        assertThat(user.getPhone_number()).isEqualTo(findUser.getPhone_number());
        assertThat(user.getNickname()).isEqualTo(findUser.getNickname());
    }

    @Test
    public void 닉네임_잘_찾는지() {
        //given
        User user = User.builder()
                .email("user1@a.com")
                .password("user1")
                .username("user1")
                .phone_number("01012345678")
                .nickname("user1")
                .build();
        userRepository.save(user);

        //when
        User findUser = userRepository.findByNickname("user1").get();

        //then
        assertThat(user.getUsername()).isEqualTo(findUser.getUsername());
        assertThat(user.getEmail()).isEqualTo(findUser.getEmail());
        assertThat(user.getPassword()).isEqualTo(findUser.getPassword());
        assertThat(user.getPhone_number()).isEqualTo(findUser.getPhone_number());
        assertThat(user.getNickname()).isEqualTo(findUser.getNickname());
    }

    @Test
    public void 핸드폰번호_잘_찾는지() {
        //given
        User user = User.builder()
                .email("user1@a.com")
                .password("user1")
                .username("user1")
                .phone_number("user1")
                .nickname("user1")
                .build();
        userRepository.save(user);

        //when
        User findUser = userRepository.findByPhoneNumber("user1").get();

        //then
        assertThat(user.getUsername()).isEqualTo(findUser.getUsername());
        assertThat(user.getEmail()).isEqualTo(findUser.getEmail());
        assertThat(user.getPassword()).isEqualTo(findUser.getPassword());
        assertThat(user.getPhone_number()).isEqualTo(findUser.getPhone_number());
        assertThat(user.getNickname()).isEqualTo(findUser.getNickname());
    }

    @Test
    public void findAll_테스트() {
        //given
        List<User> beforeUsers = userRepository.findAll();
        User user1 = User.builder()
                .email("user1@a.com")
                .password("user1")
                .username("user1")
                .phone_number("01012345678")
                .nickname("user1")
                .build();
        User user2 = User.builder()
                .email("user2@a.com")
                .password("user2")
                .username("user2")
                .phone_number("01022345678")
                .nickname("user2")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        //when
        List<User> afterUsers = userRepository.findAll();

        //then
        assertThat(afterUsers.size()-beforeUsers.size()).isEqualTo(2);
    }

    @Test
    public void update_테스트() {
        //given
        User user1 = User.builder()
                .email("user1@a.com")
                .password("user1")
                .username("user1")
                .phone_number("01012345678")
                .nickname("user1")
                .build();
        userRepository.save(user1);
        User user2 = User.builder()
                .email("user2@a.com")
                .password("user2")
                .username("user2")
                .phone_number("01022345678")
                .nickname("user2")
                .build();

        //when
        userRepository.update(user1.getUser_id(), user2);
        User updateUser = userRepository.findById(user1.getUser_id()).get();

        //then
        assertThat(updateUser.getUsername()).isEqualTo("user2");
        assertThat(updateUser.getEmail()).isEqualTo("user2@a.com");
        assertThat(updateUser.getPassword()).isEqualTo("user2");
        assertThat(updateUser.getPhone_number()).isEqualTo("01022345678");
        assertThat(updateUser.getNickname()).isEqualTo("user2");
    }
}
